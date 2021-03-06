package com.lyl.webElf.dao;

import com.lyl.webElf.domain.GuessData;
import com.lyl.webElf.domain.GuessDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GuessDataMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guess_data
     *
     * @mbg.generated Sun May 24 17:27:15 CST 2020
     */
    long countByExample(GuessDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guess_data
     *
     * @mbg.generated Sun May 24 17:27:15 CST 2020
     */
    int deleteByExample(GuessDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guess_data
     *
     * @mbg.generated Sun May 24 17:27:15 CST 2020
     */
    int insert(GuessData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guess_data
     *
     * @mbg.generated Sun May 24 17:27:15 CST 2020
     */
    int insertSelective(GuessData record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guess_data
     *
     * @mbg.generated Sun May 24 17:27:15 CST 2020
     */
    List<GuessData> selectByExample(GuessDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guess_data
     *
     * @mbg.generated Sun May 24 17:27:15 CST 2020
     */
    int updateByExampleSelective(@Param("record") GuessData record, @Param("example") GuessDataExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table guess_data
     *
     * @mbg.generated Sun May 24 17:27:15 CST 2020
     */
    int updateByExample(@Param("record") GuessData record, @Param("example") GuessDataExample example);
}