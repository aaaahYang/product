package com.product.dao.repository.unit;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * 模糊分页查询工具类
 * @param <T>
 */
public class SpecificationUnit<T> implements Specification {


    private T t;

    public SpecificationUnit(T t){
        this.t =t;
    }


    /**
     * 模糊查询
     * @param root
     * @param criteriaQuery
     * @param criteriaBuilder
     * @return
     */
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();


        Class<?> clz = t.getClass();
        for(Field f : clz.getDeclaredFields()){

            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(f.getName(),clz);
                Method method = descriptor.getReadMethod();

                Object o = method.invoke(t);

                if ( o != null){
                    Predicate predicate = criteriaBuilder.like(root.get(f.getName()),"%"+o+"%");
                    predicates.add(predicate);
                }
            } catch (IntrospectionException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return criteriaQuery.getRestriction();
    }
}
