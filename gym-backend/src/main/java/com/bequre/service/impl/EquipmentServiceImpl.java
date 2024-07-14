package com.bequre.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bequre.mapper.EquipmentMapper;
import com.bequre.pojo.Coach;
import com.bequre.pojo.Equipment;
import com.bequre.pojo.PageDTO;
import com.bequre.pojo.query.EquipmentQuery;
import com.bequre.service.EquipmentService;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService{

    /**
     *  器材分页条件查询
     * @param equipmentQuery
     * @return
     */
    public PageDTO<Equipment> queryEquipmentPage(EquipmentQuery equipmentQuery) {

        //1. 构建分页条件
        Page<Equipment> p = equipmentQuery.toMpPage();

        //2. 分页条件查询
        String name = equipmentQuery.getName();
        String status = equipmentQuery.getStatus();
        Page<Equipment> page = lambdaQuery()
                .eq(name != null, Equipment::getName, name)
                .eq(status != null, Equipment::getStatus, status)
                .page(p);

        //3. 封装分页结果
        return new PageDTO<>(page.getTotal(),page.getPages(),page.getRecords());

    }

    /**
     *  根据器材名称查询器材
     * @param name
     * @return
     */
    public Equipment getByName(String name) {
        return lambdaQuery()
                .eq(Equipment::getName, name)
                .one();
    }

    /**
     *  增加器材数量
     * @param equipment
     */
    public void addQuantity(Equipment equipment) {
        Equipment e = getByName(equipment.getName());
        lambdaUpdate()
                .set(Equipment::getQuantity, e.getQuantity() + equipment.getQuantity())
                .eq(Equipment::getName, equipment.getName())
                .update();

    }


}
