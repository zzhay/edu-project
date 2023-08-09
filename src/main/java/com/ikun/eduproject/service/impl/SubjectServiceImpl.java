package com.ikun.eduproject.service.impl;

import com.ikun.eduproject.dao.SubjectDao;
import com.ikun.eduproject.pojo.Subject;
import com.ikun.eduproject.service.SubjectService;
import com.ikun.eduproject.vo.ResultVO;
import com.ikun.eduproject.vo.StatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zzhay
 * @Date 2023/7/28/028
 * SubjectServiceImpl是学科相关功能的Service实现类。
 * 提供了学科分类管理等相关功能的具体实现。
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Resource
    private SubjectDao subjectDao;

    /**
     * 获取所有学科
     * @return ResultVO
     */
    @Override
    public ResultVO<Map<String, List<String>>> getAll() {
        //查出所有学科类别信息
        List<Subject> subjects = subjectDao.selectAll();
        //封装到map中
        Map<String, List<String>> categoryMap = new HashMap<>();
        for (Subject subject : subjects) {
            //学科名
            String subName = subject.getSubName();
            //学科类别
            String subCategory = subject.getSubCategory();
            if (!categoryMap.containsKey(subCategory)) {
                categoryMap.put(subCategory, new ArrayList<>());
            }
            categoryMap.get(subCategory).add(subName);
        }
        return new ResultVO<>(StatusVO.SELECT_OK, "查询成功", categoryMap);
    }
}
