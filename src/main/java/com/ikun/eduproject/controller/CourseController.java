package com.ikun.eduproject.controller;

import com.ikun.eduproject.pojo.Course;
import com.ikun.eduproject.pojo.CourseAudit;
import com.ikun.eduproject.pojo.ElasticsearchCourse;
import com.ikun.eduproject.service.CourseService;
import com.ikun.eduproject.vo.GetCourseCheckedVO;
import com.ikun.eduproject.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author zzhay
 * @Date 2023/7/27/027
 */
@RestController
@RequestMapping("/course")
@CrossOrigin
@Api(value = "提供课程管理的接口", tags = "课程管理")
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 新增课程
     *
     * @param course 课程对象
     * @return ResultVO
     */
    @ApiOperation("新增课程")
    @PostMapping("/addCourse")
    public ResultVO<String> addCourse(@RequestBody @Valid Course course) {
        return courseService.addCourse(course);
    }

    /**
     * 教师查看已上架课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @ApiOperation("教师查看已上架课程")
    @GetMapping("/getCourse1")
    public ResultVO<List<Course>> getCourse1(@RequestParam("userId") @NotNull(message = "用户id不能为空") int userId) {
        return courseService.getCourse1(userId);
    }

    /**
     * 教师查看待审核课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @ApiOperation("教师查看待审核课程")
    @GetMapping("/getCourse2")
    public ResultVO<List<CourseAudit>> getCourse2(@RequestParam("userId") @NotNull(message = "用户id不能为空") int userId) {
        return courseService.getCourse2(userId);
    }

    /**
     * 教师查看审核未通过课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @ApiOperation("教师查看审核未通过课程")
    @GetMapping("/getCourse3")
    public ResultVO<List<CourseAudit>> getCourse3(@RequestParam("userId") @NotNull(message = "用户id不能为空") int userId) {
        return courseService.getCourse3(userId);
    }

    /**
     * 教师查看已下架课程
     *
     * @param userId 用户id
     * @return ResultVO
     */
    @ApiOperation("教师查看已下架课程")
    @GetMapping("/getCourse4")
    public ResultVO<List<Course>> getCourse4(@RequestParam("userId") @NotNull(message = "用户id不能为空") int userId) {
        return courseService.getCourse4(userId);
    }

    /**
     * 教师修改已上架课程信息
     *
     * @param course 课程对象
     * @return ResultVO
     */
    @ApiOperation("教师修改已上架课程信息")
    @PostMapping("/updateCourse")
    public ResultVO<String> updateCourse(@RequestBody @Valid Course course) {
        return courseService.updateCourse(course);
    }

    /**
     * 教师修改审核未通过课程信息
     *
     * @param course 课程对象
     * @return ResultVO
     */
    @ApiOperation("教师修改审核未通过课程信息")
    @PostMapping("/updateCheckNo")
    public ResultVO<String> updateCheckNo(@RequestBody @Valid Course course) {
        return courseService.updateCheckNo(course);
    }

    /**
     * 教师下架课程
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @ApiOperation("教师下架课程")
    @PostMapping("/updateStatu")
    public ResultVO<String> updateStatu(@RequestParam("courseId") @NotNull(message = "课程id不能为空") Integer courseId) {
        return courseService.updateStatu(courseId);
    }

    /**
     * 教师删除未通过课程
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @ApiOperation("教师删除未通过课程")
    @PostMapping("/deleteReq")
    public ResultVO<String> deleteReq(@RequestParam("courseId") @NotNull(message = "课程id不能为空") Integer courseId) {
        return courseService.deleteReq(courseId);
    }

    /**
     * 管理员查看所有待审核课程
     *
     * @return ResultVO
     */
    @ApiOperation("管理员查看所有待审核课程")
    @GetMapping("/getChecked")
    public ResultVO<List<GetCourseCheckedVO>> getChecked() {
        return courseService.getChecked();
    }

    /**
     * 管理员审核课程
     *
     * @param courseId 课程id
     * @param checked  审核情况
     * @param reason   理由
     * @return ResultVO
     */
    @ApiOperation("管理员审核课程")
    @PostMapping("/updateChecked")
    public ResultVO<String> updateChecked(@RequestParam("courseId") @NotNull(message = "课程id不能为空") Integer courseId,
                                          @RequestParam("checked") @NotNull(message = "审核状态不能为空") Integer checked,
                                          @RequestParam("reason") @NotNull(message = "理由不能为空") String reason) {
        return courseService.updateChecked(courseId, checked, reason);
    }

    /**
     * 按照学科类别查看课程
     *
     * @param category 课程类别
     * @return ResultVO
     */
    @ApiOperation("按照学科类别查看课程")
    @GetMapping("/getByCategory")
    public ResultVO<List<ElasticsearchCourse>> getByCategory(@RequestParam("category") @NotNull(message = "学科类别不能为空") String category) {
        return courseService.getByCategory(category);
    }

    /**
     * 按照学科类别查看课程并按价格排序
     *
     * @param category 课程类别
     * @param sort     排序方式（0：升序，1：降序）
     * @return ResultVO
     */
    @ApiOperation("按照学科类别查看课程并按价格排序")
    @GetMapping("/getByCategoryOrderByPrice")
    public ResultVO<List<ElasticsearchCourse>> getByCategoryOrderByPrice(@RequestParam("category") @NotNull(message = "学科类别不能为空") String category,
                                                                         @RequestParam("sort") @NotNull Integer sort) {
        return courseService.getByCategoryOrderByPrice(category, sort);
    }

    /**
     * 按照学科名查看课程
     *
     * @param subName 学科名
     * @return ResultVO
     */
    @ApiOperation("按照学科名查看课程")
    @GetMapping("/getBySubName")
    public ResultVO<List<ElasticsearchCourse>> getBySubName(@RequestParam("subName") @NotNull(message = "学科名不能为空") String subName) {
        return courseService.getBySubName(subName);
    }

    /**
     * 按照学科名查看课程并按价格排序
     *
     * @param subName 学科名
     * @param sort    排序方式（0：升序，1：降序）
     * @return ResultVO
     */
    @ApiOperation("按照学科名查看课程并按价格排序")
    @GetMapping("/getBySubNameOrderByPrice")
    public ResultVO<List<ElasticsearchCourse>> getBySubNameOrderByPrice(@RequestParam("subName") @NotNull(message = "学科名不能为空") String subName,
                                                                        @RequestParam("sort") @NotNull Integer sort) {
        return courseService.getBySubNameOrderByPrice(subName, sort);
    }

    /**
     * 按照课程名名称模糊查询
     *
     * @param name 课程名
     * @return ResultVO
     */
    @ApiOperation("按照课程名名称模糊查询")
    @GetMapping("/queryFuzzy")
    public ResultVO<List<ElasticsearchCourse>> queryByNameOrAuthorFuzzy(@RequestParam("name") @NotNull(message = "课程名或作者不能为空") String name) {
        return courseService.queryByNameOrAuthorFuzzy(name);
    }

    /**
     * 按照课程名名称模糊查询并按价格排序
     *
     * @param name 课程名
     * @param sort 排序方式（0：升序，1：降序）
     * @return ResultVO
     */
    @ApiOperation("按照课程名名称模糊查询并按价格排序")
    @GetMapping("/queryFuzzyOrderByPrice")
    public ResultVO<List<ElasticsearchCourse>> queryByNameOrAuthorFuzzyOrderByPrice(@RequestParam("name") @NotNull(message = "课程名或作者不能为空") String name,
                                                                                    @RequestParam("sort") @NotNull Integer sort) {
        return courseService.queryByNameOrAuthorFuzzyOrderByPrice(name, sort);
    }

    /**
     * 随机获取4条课程
     *
     * @return ResultVO
     */
    @ApiOperation("随机获取4条课程")
    @GetMapping("/queryRandomCourses")
    public ResultVO<List<ElasticsearchCourse>> queryRandom() {
        return courseService.queryRandomCourses();
    }

    /**
     * 获取热门课程
     *
     * @return ResultVO
     */
    @ApiOperation("获取热门课程")
    @GetMapping("/getPopularCourses")
    public ResultVO<List<ElasticsearchCourse>> getPopularCourses() {
        return courseService.getPopularCourses();
    }

    /**
     * 增加搜索热值
     *
     * @param courseId 课程id
     * @return ResultVO
     */
    @ApiOperation("增加搜索热值")
    @PostMapping("/addSearchFrequency")
    public ResultVO<String> addSearchFrequency(@RequestParam("courseId") @NotNull(message = "课程id不能为空") Integer courseId) {
        return courseService.incrementSearchFrequency(courseId);
    }
}
