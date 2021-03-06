package com.rstech.wordwatch.dao.mapper;

import com.rstech.wordwatch.dao.WDUserReport;
import com.rstech.wordwatch.dao.WDUserReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WDUserReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int countByExample(WDUserReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int deleteByExample(WDUserReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int deleteByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int insert(WDUserReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int insertSelective(WDUserReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    List<WDUserReport> selectByExample(WDUserReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    WDUserReport selectByPrimaryKey(Long ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int updateByExampleSelective(@Param("record") WDUserReport record, @Param("example") WDUserReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int updateByExample(@Param("record") WDUserReport record, @Param("example") WDUserReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int updateByPrimaryKeySelective(WDUserReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wd_user_report_view
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    int updateByPrimaryKey(WDUserReport record);
}