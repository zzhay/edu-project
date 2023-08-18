package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.*;
import com.ikun.eduproject.error.*;
import com.ikun.eduproject.es.EsCourseRepository;
import com.ikun.eduproject.pojo.*;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.utils.AliOssUtils;
import com.ikun.eduproject.utils.EmailUtil;
import com.ikun.eduproject.vo.EmailMsgVO;
import com.ikun.eduproject.vo.GetCourseCheckedVO;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.RandomScoreFunctionBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 * CourseServiceImpl是课程相关功能的Service实现类。
 * 提供了课程管理等相关功能的具体实现。
 */
@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseDao courseDao;
    @Resource
    private CourseAuditDao courseAuditDao;
    @Resource
    private AssignmentsDao assignmentsDao;
    @Resource
    private SubjectDao subjectDao;
    @Resource
    private PeriodDao periodDao;

    @Resource
    private EsCourseRepository esCourseRepository;
    @Resource
    ElasticsearchOperations elasticsearchOperations;
    @Resource
    private AliOssUtils aliOSSUtils;
    @Resource
    private EmailUtil emailUtil;

    /**
     * 新增课程
     *
     * @param course 课程信息
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = AddCourseException.class)
    public ResultVO<String> addCourse(Course course) {
        //根据教师id和课程名查出课程id（课程主表和课程审核版本表）
        Integer courseId = courseDao.selectByUIdAndName(course.getUserId(), course.getName());
        Integer courseId1 = courseAuditDao.selectByUIdAndName(course.getUserId(), course.getName());
        //如果id存在，则该教师名下已有该课程名称，报冲突
        if (courseId != null || courseId1 != null) {
            return new ResultVO<>(StatusVO.INSERT_NO, "课程名已经存在", null);
        }

        //新增课程
        int i = courseDao.insertCourse(course);
        if (i <= 0) {
            return new ResultVO<>(StatusVO.INSERT_NO, "添加失败", null);
        }
        //查询出课程id
        courseId = courseDao.selectByUIdAndName(course.getUserId(), course.getName());
        course.setCourseId(courseId);
        //将课程信息存到课程审核版本表
        try {
            int i1 = courseAuditDao.insertCourseAudit(course);
            if (i1 > 0) {
                return new ResultVO<>(StatusVO.INSERT_OK, "添加成功，请等待审核", null);
            } else {
                //更新失败，抛出异常回滚事务
                throw new AddCourseException("课程添加失败");
            }
        } catch (AddCourseException e) {
            return new ResultVO<>(StatusVO.INSERT_NO, "添加失败", null);
        }
    }

    /**
     * 教师查看已上架课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<Course>> getCourse1(Integer userId) {
        List<Course> list = courseDao.selectByUserId(userId);
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", list);
    }

    /**
     * 教师查看待审核课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<CourseAudit>> getCourse2(Integer userId) {
        List<CourseAudit> list = courseAuditDao.selectByUserIdChecking(userId);
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", list);
    }

    /**
     * 教师查看审核未通过课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<CourseAudit>> getCourse3(Integer userId) {
        List<CourseAudit> list = courseAuditDao.selectByUserIdCheckNo(userId);
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", list);
    }

    /**
     * 教师查看已下架课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @Override
    public ResultVO<List<Course>> getCourse4(Integer userId) {
        List<Course> list = courseDao.selectByUserId2(userId);
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", list);
    }

    /**
     * 教师下架课程
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = ChangeCourseStatuException.class)
    public ResultVO<String> updateStatu(Integer courseId) {
        //判断该课程是否有作业未批改
        List<Assignments> list = assignmentsDao.selectByCourseId(courseId);
        if (list.size() != 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "下架失败，还有作业未批改", null);
        }
        //下架课程
        int i = courseDao.updateStatu(courseId);
        if (i <= 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "下架失败", null);
        }
        try {
            //下架课程审核版本
            int i1 = courseAuditDao.updateStatu(courseId);
            if (i1 <= 0) {
                //修改失败抛出异常，事务回滚
                throw new ChangeCourseStatuException("课程下架失败");
            } else {
                //从ES中删除
                esCourseRepository.deleteById(courseId);
                return new ResultVO<>(StatusVO.UPDATE_OK, "下架成功", null);
            }
        } catch (ChangeCourseStatuException e) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "下架失败", null);
        }
    }

    /**
     * 教师修改已上架课程信息（只修改课程审核版本，审核通过后才同步课程）
     *
     * @param course 课程信息
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultVO<String> updateCourse(Course course) {
        //获取原课程信息
        Course courseAudit = courseAuditDao.selectByCoursrId(course.getCourseId());
        //判断课程是否上架
        if (courseAudit.getStatu() == 1 && courseAudit.getChecked() == 1) {
            //根据教师id和课程名查出课程id,判断同一教师下课程名是否重复
            Integer courseId = courseDao.selectByUIdAndName(courseAudit.getUserId(), course.getName());
            if (courseId != null && !courseId.equals(course.getCourseId())) {
                return new ResultVO<>(StatusVO.UPDATE_NO, "课程名重复", null);
            }
            Integer courseId1 = courseAuditDao.selectByUIdAndName(courseAudit.getUserId(), course.getName());
            if (courseId1 != null && !courseId1.equals(course.getCourseId())) {
                return new ResultVO<>(StatusVO.UPDATE_NO, "课程名重复", null);
            }
            //更新课程审核版本
            int i = courseAuditDao.updateCourseAudit(course);
            if (i > 0) {
                return new ResultVO<>(StatusVO.UPDATE_OK, "更新成功，请等待审核", null);
            } else {
                return new ResultVO<>(StatusVO.UPDATE_NO, "更新失败", null);
            }
        }
        //判断课程是否在修改流程中
        if (courseAudit.getStatu() == 2 && (courseAudit.getChecked() == 0 || courseAudit.getChecked() == 2)) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "课程已提交过修改，流程还未结束，不能重复提交", null);
        }
        return null;
    }

    /**
     * 教师修改审核未通过课程信息
     *
     * @param course 课程信息
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ResultVO<String> updateCheckNo(Course course) {
        //判断课程名重复
        Integer courseId = courseDao.selectByUIdAndName(course.getUserId(), course.getName());
        if (courseId != null && !courseId.equals(course.getCourseId())) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "课程名重复", null);
        }
        Integer courseId1 = courseAuditDao.selectByUIdAndName(course.getUserId(), course.getName());
        if (courseId1 != null && !courseId1.equals(course.getCourseId())) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "课程名重复", null);
        }
        //更新课程审核版本
        int i = courseAuditDao.updateCourseAudit(course);
        if (i <= 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "更新失败", null);
        }
        return new ResultVO<>(StatusVO.UPDATE_OK, "更新成功，请等待审核", null);
    }

    /**
     * 教师删除申请
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = {DelCourseException.class, AliOSSDeleteException.class})
    public ResultVO<String> deleteReq(Integer courseId) {
        //获取原课程信息
        Course course = courseDao.selectByCourseId(courseId);
        //判断该申请是否是已上架课程的修改申请
        if (course.getStatu() == 1 && course.getChecked() == 1) {
            //获取课程审核版本的课程信息
            Course courseAudit = courseAuditDao.selectByCoursrId(courseId);
            try {
                //比较审核版本课程的图片url和文件url是否和原课程的相同，不同则删除课程审核版本的原url
                if (!Objects.equals(course.getImageUrl(), courseAudit.getImageUrl())) {
                    boolean b = aliOSSUtils.deleteImageByUrl(courseAudit.getImageUrl());
                    if (!b) {
                        throw new AliOSSDeleteException("原文件删除失败");
                    }
                }
                if (!Objects.equals(course.getContentUrl(), courseAudit.getContentUrl())) {
                    boolean b = aliOSSUtils.deleteImageByUrl(courseAudit.getContentUrl());
                    if (!b) {
                        throw new AliOSSDeleteException("原文件删除失败");
                    }
                }
            } catch (AliOSSDeleteException e) {
                return new ResultVO<>(StatusVO.UPDATE_NO, "删除失败", null);
            }
            //恢复课程审核版本
            int i = courseAuditDao.updateFromCourse(course);
            if (i > 0) {
                return new ResultVO<>(StatusVO.UPDATE_OK, "删除成功", null);
            } else {
                return new ResultVO<>(StatusVO.UPDATE_NO, "删除失败", null);
            }
        }
        //删除新增课程的申请
        if (course.getStatu() == 2 && course.getChecked() == 0) {
            //删除课程表和课程审核版本表中数据
            if (courseDao.deleteCourse(courseId) <= 0) {
                return new ResultVO<>(StatusVO.UPDATE_NO, "删除失败", null);
            }
            try {
                if (courseAuditDao.deleteCourseAudit(courseId) > 0) {
                    //删除图片和文件
                    String imageUrl = course.getImageUrl();
                    String contentUrl = course.getContentUrl();
                    try {
                        boolean b = aliOSSUtils.deleteImageByUrl(imageUrl);
                        boolean b1 = aliOSSUtils.deleteImageByUrl(contentUrl);
                        if (!b && !b1) {
                            throw new AliOSSDeleteException("原文件删除失败");
                        }
                    } catch (AliOSSDeleteException e) {
                        return new ResultVO<>(StatusVO.UPDATE_NO, "删除失败", null);
                    }
                    return new ResultVO<>(StatusVO.UPDATE_OK, "删除成功", null);
                } else {
                    throw new DelCourseException("课程申请删除失败");
                }
            } catch (DelCourseException e) {
                return new ResultVO<>(StatusVO.UPDATE_NO, "删除失败", null);
            }

        }
        return null;
    }

    /**
     * 查看所有待审核课程
     *
     * @return ResultVO
     */
    @Override
    public ResultVO<List<GetCourseCheckedVO>> getChecked() {
        List<GetCourseCheckedVO> lists = courseAuditDao.selectAllCheck();
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", lists);
    }

    /**
     * 课程审核
     *
     * @param courseId 课程id
     * @param checked  审核状态
     * @return ResultVO
     */
    @Override
    @Transactional(rollbackFor = UpdateCourseException.class)
    public ResultVO<String> updateChecked(Integer courseId, Integer checked, String reason) {
        int i1 = courseAuditDao.updateCheck(courseId, checked);
        if (i1 <= 0) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "审核失败", null);
        }
        String email = courseDao.selectEmailByCourseId(courseId);
        Course courseAudit = courseAuditDao.selectByCoursrId(courseId);
        //审核通过
        if (checked == 1) {
            //课程信息
            Course course = courseDao.selectByCourseId(courseId);
            //获取原图片和附件url
            String imageUrl = course.getImageUrl();
            String contentUrl = course.getContentUrl();
            //设置课程课时属性
            Periods periods = new Periods();
            periods.setCourseId(courseId);
            periods.setPeriods(courseAudit.getPeriods());
            periods.setContentUrl(courseAudit.getContentUrl());

            //查询该课程课时信息
            List<Periods> periods1 = periodDao.selectByCourseId(courseId);
            //判断是普通修改还是课时修改或新增（没有课时信息）
            if (!Objects.equals(course.getPeriods(), courseAudit.getPeriods()) || periods1.size()==0) {
                //修改课时或新增（没有课时信息）
                try {
                    int i = periodDao.insert(periods);
                    if (i <= 0) {
                        throw new UpdateCourseException("课程更新失败");
                    }
                } catch (AliOSSDeleteException e) {
                    return new ResultVO<>(StatusVO.UPDATE_NO, "审核失败", null);
                }
            } else if (Objects.equals(course.getPeriods(), courseAudit.getPeriods())) {
                //普通修改
                try {
                    int i = periodDao.update(periods);
                    if (i <= 0) {
                        throw new UpdateCourseException("课程更新失败");
                    }
                } catch (AliOSSDeleteException e) {
                    return new ResultVO<>(StatusVO.UPDATE_NO, "审核失败", null);
                }
            }
            //比较url是否相同，不同则删除原url
            try {
                if (!Objects.equals(imageUrl, courseAudit.getImageUrl())) {
                    boolean b = aliOSSUtils.deleteImageByUrl(imageUrl);
                    if (!b) {
                        throw new AliOSSDeleteException("原文件删除失败");
                    }
                }
                if (!Objects.equals(contentUrl, courseAudit.getContentUrl())) {
                    boolean b = aliOSSUtils.deleteImageByUrl(contentUrl);
                    if (!b) {
                        throw new AliOSSDeleteException("原文件删除失败");
                    }
                }
            } catch (AliOSSDeleteException e) {
                return new ResultVO<>(StatusVO.UPDATE_NO, "审核失败", null);
            }
            //设置课程状态属性
            courseAudit.setStatu(1);
            courseAudit.setChecked(1);
            try {
                //同步课程
                int i = courseDao.updateCourse(courseAudit);
                if (i <= 0) {
                    throw new UpdateCourseException("课程更新失败");
                }
            } catch (UpdateCourseException e) {
                return new ResultVO<>(StatusVO.UPDATE_NO, "审核失败", null);
            }

            // 审核通过时，添加课程到Elasticsearch
            //将Course类对象转换为ElasticsearchCourse对象
            ElasticsearchCourse esCourse = ElasticsearchCourse.fromCourse(courseAudit);
            //设置subCategory
            String category = subjectDao.selectSubCategoryBySubName(courseAudit.getSubName());
            esCourse.setSubCategory(category);
            //设置searchFrequency
            esCourse.setSearchFrequency(0);
            esCourseRepository.save(esCourse);
            //发送邮件通知
            emailUtil.sendMessage(email, EmailMsgVO.COURSE, EmailMsgVO.coursePassed(courseAudit.getName()));
            return new ResultVO<>(StatusVO.UPDATE_OK, "审核成功", null);
        }
        //审核不通过
        if (checked == 2) {
            //发送邮件通知
            emailUtil.sendMessage(email, EmailMsgVO.COURSE, EmailMsgVO.courseNotPassed(courseAudit.getName(), reason));
            return new ResultVO<>(StatusVO.UPDATE_OK, "审核成功", null);
        }
        return null;
    }

    /**
     * 按照学科类别查看课程
     *
     * @param category 学科类别
     * @return ResultVO
     */
    @Override
    public ResultVO<List<ElasticsearchCourse>> getByCategory(String category) {
        //查询
        List<ElasticsearchCourse> courses = esCourseRepository.findBySubCategory(category);
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", courses);
    }

    /**
     * 按照学科类别查看课程并按价格排序
     *
     * @param category 学科类别
     * @param sort     排序方式（0：升序，1：降序）
     * @return ResultVO
     */
    @Override
    public ResultVO<List<ElasticsearchCourse>> getByCategoryOrderByPrice(String category, Integer sort) {
        List<ElasticsearchCourse> courseList;
        if (sort.equals(0)) {
            //升序
            courseList = esCourseRepository.findBySubCategoryOrderByPriceAsc(category);
        } else if (sort.equals(1)) {
            //降序
            courseList = esCourseRepository.findBySubCategoryOrderByPriceDesc(category);
        } else {
            return new ResultVO<>(StatusVO.SELECT_NO, "参数错误", null);
        }
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", courseList);
    }

    /**
     * 按照学科名查询
     *
     * @param subName 学科名
     * @return ResultVO
     */
    @Override
    public ResultVO<List<ElasticsearchCourse>> getBySubName(String subName) {
        List<ElasticsearchCourse> courses = esCourseRepository.findBySubName(subName);
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", courses);
    }

    /**
     * 按照学科名查询并按价格排序
     *
     * @param subName 学科名
     * @param sort    排序方式（0：升序，1：降序）
     * @return ResultVO
     */
    @Override
    public ResultVO<List<ElasticsearchCourse>> getBySubNameOrderByPrice(String subName, Integer sort) {
        List<ElasticsearchCourse> courseList;
        if (sort.equals(0)) {
            courseList = esCourseRepository.findBySubNameOrderByPriceAsc(subName);
        } else if (sort.equals(1)) {
            courseList = esCourseRepository.findBySubNameOrderByPriceDesc(subName);
        } else {
            return new ResultVO<>(StatusVO.SELECT_NO, "参数错误", null);
        }
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", courseList);
    }

    /**
     * 按照课程名模糊查询
     *
     * @param name 课程名或作者
     * @return ResultVO
     */
    @Override
    public ResultVO<List<ElasticsearchCourse>> queryByNameOrAuthorFuzzy(String name) {
        try {
            //调用查询方法
            List<ElasticsearchCourse> courseList = esCourseRepository.findByNameFuzzy(name);
            if (courseList.isEmpty()) {
                return new ResultVO<>(StatusVO.SELECT_NO, "没有找到相关课程", courseList);
            } else {
                return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", courseList);
            }
            //捕获异常
        } catch (ElasticsearchException e) {
            e.printStackTrace();
            return new ResultVO<>(StatusVO.SELECT_NO, "查询异常", null);
        }
    }

    /**
     * 按照课程名模糊查询并按价格排序
     *
     * @param name 课程名
     * @param sort 排序方式（0：升序，1：降序）
     * @return ResultVO
     */
    @Override
    public ResultVO<List<ElasticsearchCourse>> queryByNameOrAuthorFuzzyOrderByPrice(String name, Integer sort) {
        List<ElasticsearchCourse> lists;
        if (sort.equals(0)) {
            // 构建查询条件，按照课程名进行模糊查询
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery("name", name).operator(Operator.OR))
                    .build();

            // 添加按价格升序排序规则
            searchQuery.addSort(Sort.by(Sort.Direction.ASC, "price"));
            // 执行查询并返回结果
            SearchHits<ElasticsearchCourse> searchHits = elasticsearchOperations.search(searchQuery, ElasticsearchCourse.class);
            // 将SearchHits转换为列表集合
            lists = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        } else if (sort.equals(1)) {
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery("name", name).operator(Operator.OR))
                    .build();

            searchQuery.addSort(Sort.by(Sort.Direction.DESC, "price"));
            SearchHits<ElasticsearchCourse> searchHits = elasticsearchOperations.search(searchQuery, ElasticsearchCourse.class);
            lists = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        } else {
            return new ResultVO<>(StatusVO.SELECT_NO, "参数错误", null);
        }
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", lists);
    }

    /**
     * 随机获取4个课程数据的
     *
     * @return ResultVO
     */
    @Override
    public ResultVO<List<ElasticsearchCourse>> queryRandomCourses() {
        // 获取每小时的seed
        long seed = getHourSeed();
        // 构建查询条件：匹配所有文档
        QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        // 构建随机得分函数，使用每小时的seed作为种子
        RandomScoreFunctionBuilder randomScoreFunction = new RandomScoreFunctionBuilder();
        randomScoreFunction.seed(seed);
        // 构建function_score查询：将随机得分函数与查询条件结合，实现随机排序
        QueryBuilder functionScoreQuery = QueryBuilders.functionScoreQuery(queryBuilder, randomScoreFunction)
                .boostMode(CombineFunction.REPLACE);
        // 构建NativeSearchQuery：将function_score查询与分页配置结合，并获取前4条数据作为随机课程
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(functionScoreQuery)
                .withPageable(PageRequest.of(0, 4))
                .build();
        // 执行查询，并获取查询结果
        SearchHits<ElasticsearchCourse> searchHits = elasticsearchOperations.search(searchQuery, ElasticsearchCourse.class);
        // 将SearchHits转换为列表集合
        List<ElasticsearchCourse> lists = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        return new ResultVO<>(StatusVO.SELECT_OK, "获取成功", lists);
    }

    /**
     * 获取每小时的seed
     *
     * @return
     */
    private long getHourSeed() {
        // 获取当前时间的小时部分
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime currentHour = currentTime.withMinute(0).withSecond(0).withNano(0);
        // 转换为Epoch秒数
        return currentHour.toInstant(ZoneOffset.UTC).getEpochSecond();
    }

    /**
     * 获取最热门的10个课程
     *
     * @return ResultVO
     */
    @Override
    public ResultVO<List<ElasticsearchCourse>> getPopularCourses() {
        // 创建排序对象，按照searchFrequency字段降序排列
        Sort sort = Sort.by(Sort.Direction.DESC, "searchFrequency");
        // 创建分页对象，只返回前10条数据
        Pageable pageable = PageRequest.of(0, 10, sort);
        // 查询热门课程
        List<ElasticsearchCourse> lists = esCourseRepository.findAll(pageable).getContent();
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", lists);
    }

    /**
     * 根据课程id对searchFrequency字段加一
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @Override
    public ResultVO<String> incrementSearchFrequency(Integer courseId) {
        // 根据课程id从Elasticsearch中获取课程对象
        ElasticsearchCourse course = esCourseRepository.findById(courseId).orElse(null);
        // 判断课程对象是否存在
        if (course == null) {
            return new ResultVO<>(StatusVO.UPDATE_NO, "课程不存在", null);
        }
        // 将searchFrequency字段加一
        course.setSearchFrequency(course.getSearchFrequency() + 1);
        // 使用Update API更新数据
        elasticsearchOperations.save(course);
        return new ResultVO<>(StatusVO.UPDATE_OK, "更新成功", null);
    }

}
