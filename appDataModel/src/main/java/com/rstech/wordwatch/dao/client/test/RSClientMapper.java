package com.rstech.wordwatch.dao.client.test;

import com.rstech.wordwatch.dao.RSClient;
import com.rstech.wordwatch.dao.RSClientExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RSClientMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int countByExample(RSClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int deleteByExample(RSClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int deleteByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int insert(RSClient record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int insertSelective(RSClient record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    List<RSClient> selectByExample(RSClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    RSClient selectByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int updateByExampleSelective(@Param("record") RSClient record, @Param("example") RSClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int updateByExample(@Param("record") RSClient record, @Param("example") RSClientExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int updateByPrimaryKeySelective(RSClient record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rs_client
     *
     * @mbggenerated Tue Oct 20 19:59:29 EDT 2015
     */
    int updateByPrimaryKey(RSClient record);
}