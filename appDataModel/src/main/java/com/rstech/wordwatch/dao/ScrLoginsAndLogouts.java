package com.rstech.wordwatch.dao;

import java.util.Date;

public class ScrLoginsAndLogouts {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logins_and_logouts.ID
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    private Long ID;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logins_and_logouts.VERSION
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    private Integer VERSION;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logins_and_logouts.ACTION
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    private String ACTION;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logins_and_logouts.AUTHENTICATED_USER
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    private Long AUTHENTICATED_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logins_and_logouts.EFFECTIVE_USER
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    private Long EFFECTIVE_USER;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logins_and_logouts.LOGIN_SUCCESS
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    private String LOGIN_SUCCESS;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column logins_and_logouts.TIMESTAMP
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    private Date TIMESTAMP;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logins_and_logouts.ID
     *
     * @return the value of logins_and_logouts.ID
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public Long getID() {
        return ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logins_and_logouts.ID
     *
     * @param ID the value for logins_and_logouts.ID
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public void setID(Long ID) {
        this.ID = ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logins_and_logouts.VERSION
     *
     * @return the value of logins_and_logouts.VERSION
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public Integer getVERSION() {
        return VERSION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logins_and_logouts.VERSION
     *
     * @param VERSION the value for logins_and_logouts.VERSION
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public void setVERSION(Integer VERSION) {
        this.VERSION = VERSION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logins_and_logouts.ACTION
     *
     * @return the value of logins_and_logouts.ACTION
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public String getACTION() {
        return ACTION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logins_and_logouts.ACTION
     *
     * @param ACTION the value for logins_and_logouts.ACTION
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public void setACTION(String ACTION) {
        this.ACTION = ACTION == null ? null : ACTION.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logins_and_logouts.AUTHENTICATED_USER
     *
     * @return the value of logins_and_logouts.AUTHENTICATED_USER
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public Long getAUTHENTICATED_USER() {
        return AUTHENTICATED_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logins_and_logouts.AUTHENTICATED_USER
     *
     * @param AUTHENTICATED_USER the value for logins_and_logouts.AUTHENTICATED_USER
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public void setAUTHENTICATED_USER(Long AUTHENTICATED_USER) {
        this.AUTHENTICATED_USER = AUTHENTICATED_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logins_and_logouts.EFFECTIVE_USER
     *
     * @return the value of logins_and_logouts.EFFECTIVE_USER
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public Long getEFFECTIVE_USER() {
        return EFFECTIVE_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logins_and_logouts.EFFECTIVE_USER
     *
     * @param EFFECTIVE_USER the value for logins_and_logouts.EFFECTIVE_USER
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public void setEFFECTIVE_USER(Long EFFECTIVE_USER) {
        this.EFFECTIVE_USER = EFFECTIVE_USER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logins_and_logouts.LOGIN_SUCCESS
     *
     * @return the value of logins_and_logouts.LOGIN_SUCCESS
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public String getLOGIN_SUCCESS() {
        return LOGIN_SUCCESS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logins_and_logouts.LOGIN_SUCCESS
     *
     * @param LOGIN_SUCCESS the value for logins_and_logouts.LOGIN_SUCCESS
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public void setLOGIN_SUCCESS(String LOGIN_SUCCESS) {
        this.LOGIN_SUCCESS = LOGIN_SUCCESS == null ? null : LOGIN_SUCCESS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column logins_and_logouts.TIMESTAMP
     *
     * @return the value of logins_and_logouts.TIMESTAMP
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public Date getTIMESTAMP() {
        return TIMESTAMP;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column logins_and_logouts.TIMESTAMP
     *
     * @param TIMESTAMP the value for logins_and_logouts.TIMESTAMP
     *
     * @mbggenerated Mon Jul 04 20:46:26 EDT 2016
     */
    public void setTIMESTAMP(Date TIMESTAMP) {
        this.TIMESTAMP = TIMESTAMP;
    }
}