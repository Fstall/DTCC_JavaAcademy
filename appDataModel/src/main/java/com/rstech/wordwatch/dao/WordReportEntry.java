package com.rstech.wordwatch.dao;

import java.util.Date;

public class WordReportEntry {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.ID
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private Long ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.WD_REPORT
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private Long WD_REPORT;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.WD_NUMBER
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String WD_NUMBER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.IS_DELETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String IS_DELETED;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.STATUS
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String STATUS;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.WORD
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String WORD;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.DEFINITION
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String DEFINITION;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.VERSION
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private Integer VERSION;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.DATE_COMPLETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private Date DATE_COMPLETED;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.DATE_DELETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private Date DATE_DELETED;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.USER_ENTERED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String USER_ENTERED;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.ENTRY_PUBLISHED_DATE
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private Date ENTRY_PUBLISHED_DATE;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.IMG_URL1
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String IMG_URL1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.IMG_URL2
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String IMG_URL2;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wd_report_entry.IMG_URL3
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    private String IMG_URL3;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.ID
     *
     * @return the value of wd_report_entry.ID
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public Long getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.ID
     *
     * @param ID the value for wd_report_entry.ID
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setID(Long ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.WD_REPORT
     *
     * @return the value of wd_report_entry.WD_REPORT
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public Long getWD_REPORT() {
        return WD_REPORT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.WD_REPORT
     *
     * @param WD_REPORT the value for wd_report_entry.WD_REPORT
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setWD_REPORT(Long WD_REPORT) {
        this.WD_REPORT = WD_REPORT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.WD_NUMBER
     *
     * @return the value of wd_report_entry.WD_NUMBER
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getWD_NUMBER() {
        return WD_NUMBER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.WD_NUMBER
     *
     * @param WD_NUMBER the value for wd_report_entry.WD_NUMBER
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setWD_NUMBER(String WD_NUMBER) {
        this.WD_NUMBER = WD_NUMBER == null ? null : WD_NUMBER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.IS_DELETED
     *
     * @return the value of wd_report_entry.IS_DELETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getIS_DELETED() {
        return IS_DELETED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.IS_DELETED
     *
     * @param IS_DELETED the value for wd_report_entry.IS_DELETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setIS_DELETED(String IS_DELETED) {
        this.IS_DELETED = IS_DELETED == null ? null : IS_DELETED.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.STATUS
     *
     * @return the value of wd_report_entry.STATUS
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getSTATUS() {
        return STATUS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.STATUS
     *
     * @param STATUS the value for wd_report_entry.STATUS
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS == null ? null : STATUS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.WORD
     *
     * @return the value of wd_report_entry.WORD
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getWORD() {
        return WORD;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.WORD
     *
     * @param WORD the value for wd_report_entry.WORD
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setWORD(String WORD) {
        this.WORD = WORD == null ? null : WORD.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.DEFINITION
     *
     * @return the value of wd_report_entry.DEFINITION
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getDEFINITION() {
        return DEFINITION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.DEFINITION
     *
     * @param DEFINITION the value for wd_report_entry.DEFINITION
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setDEFINITION(String DEFINITION) {
        this.DEFINITION = DEFINITION == null ? null : DEFINITION.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.VERSION
     *
     * @return the value of wd_report_entry.VERSION
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public Integer getVERSION() {
        return VERSION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.VERSION
     *
     * @param VERSION the value for wd_report_entry.VERSION
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setVERSION(Integer VERSION) {
        this.VERSION = VERSION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.DATE_COMPLETED
     *
     * @return the value of wd_report_entry.DATE_COMPLETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public Date getDATE_COMPLETED() {
        return DATE_COMPLETED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.DATE_COMPLETED
     *
     * @param DATE_COMPLETED the value for wd_report_entry.DATE_COMPLETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setDATE_COMPLETED(Date DATE_COMPLETED) {
        this.DATE_COMPLETED = DATE_COMPLETED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.DATE_DELETED
     *
     * @return the value of wd_report_entry.DATE_DELETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public Date getDATE_DELETED() {
        return DATE_DELETED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.DATE_DELETED
     *
     * @param DATE_DELETED the value for wd_report_entry.DATE_DELETED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setDATE_DELETED(Date DATE_DELETED) {
        this.DATE_DELETED = DATE_DELETED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.USER_ENTERED
     *
     * @return the value of wd_report_entry.USER_ENTERED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getUSER_ENTERED() {
        return USER_ENTERED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.USER_ENTERED
     *
     * @param USER_ENTERED the value for wd_report_entry.USER_ENTERED
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setUSER_ENTERED(String USER_ENTERED) {
        this.USER_ENTERED = USER_ENTERED == null ? null : USER_ENTERED.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.ENTRY_PUBLISHED_DATE
     *
     * @return the value of wd_report_entry.ENTRY_PUBLISHED_DATE
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public Date getENTRY_PUBLISHED_DATE() {
        return ENTRY_PUBLISHED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.ENTRY_PUBLISHED_DATE
     *
     * @param ENTRY_PUBLISHED_DATE the value for wd_report_entry.ENTRY_PUBLISHED_DATE
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setENTRY_PUBLISHED_DATE(Date ENTRY_PUBLISHED_DATE) {
        this.ENTRY_PUBLISHED_DATE = ENTRY_PUBLISHED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.IMG_URL1
     *
     * @return the value of wd_report_entry.IMG_URL1
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getIMG_URL1() {
        return IMG_URL1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.IMG_URL1
     *
     * @param IMG_URL1 the value for wd_report_entry.IMG_URL1
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setIMG_URL1(String IMG_URL1) {
        this.IMG_URL1 = IMG_URL1 == null ? null : IMG_URL1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.IMG_URL2
     *
     * @return the value of wd_report_entry.IMG_URL2
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getIMG_URL2() {
        return IMG_URL2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.IMG_URL2
     *
     * @param IMG_URL2 the value for wd_report_entry.IMG_URL2
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setIMG_URL2(String IMG_URL2) {
        this.IMG_URL2 = IMG_URL2 == null ? null : IMG_URL2.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wd_report_entry.IMG_URL3
     *
     * @return the value of wd_report_entry.IMG_URL3
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public String getIMG_URL3() {
        return IMG_URL3;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wd_report_entry.IMG_URL3
     *
     * @param IMG_URL3 the value for wd_report_entry.IMG_URL3
     *
     * @mbggenerated Tue Aug 23 20:32:26 EDT 2016
     */
    public void setIMG_URL3(String IMG_URL3) {
        this.IMG_URL3 = IMG_URL3 == null ? null : IMG_URL3.trim();
    }
}