/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clonescriptscrapper.excelfile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author GLB-214
 */
public class GenerateCsvFile {

    public static void main(String[] args) throws IOException {
        excel();
    }

    public static void excel() throws FileNotFoundException, IOException {

        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Monster Details");
        HSSFRow rowhead = sheet.createRow((int) 0);
        rowhead.createCell((int) 0).setCellValue("S.No.");
        rowhead.createCell((int) 1).setCellValue("CATEGORY_DATA_ID");
        rowhead.createCell((int) 2).setCellValue("CATEGORY_ID");
        rowhead.createCell((int) 3).setCellValue("TITLE");
        rowhead.createCell((int) 4).setCellValue("NAME");
        rowhead.createCell((int) 5).setCellValue("CLICKS");
        rowhead.createCell((int) 6).setCellValue("ADDED_ON");
        rowhead.createCell((int) 7).setCellValue("PAGE_RANK");
        rowhead.createCell((int) 8).setCellValue("DESCRIPTION");
        rowhead.createCell((int) 9).setCellValue("DEMO_URL");
        rowhead.createCell((int) 10).setCellValue("CATEGORY_ID");
        rowhead.createCell((int) 11).setCellValue("CATEGORY_NAME");
        rowhead.createCell((int) 12).setCellValue("CATEGORY_URL");
        rowhead.createCell((int) 13).setCellValue("ISCRAWLED");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/clonescriptdirectorydb", "root", "");
            String sql = "SELECT * FROM `categories_data`,categories where categories_data.CATEGORY_ID= categories .CATEGORY_ID;";
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int k = 0;
            while (rs.next()) {

                HSSFRow row = sheet.createRow((int) k + 2);
                try {
                    row.createCell((int) 0).setCellValue(k + 1);
                } catch (Exception sd) {
                }
                try {
                    row.createCell((int) 1).setCellValue(rs.getString("CATEGORY_DATA_ID") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 2).setCellValue(rs.getString("CATEGORY_ID") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 3).setCellValue(rs.getString("TITLE") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 4).setCellValue(rs.getString("NAME") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 5).setCellValue(rs.getString("CLICKS") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 6).setCellValue(rs.getString("ADDED_ON") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 7).setCellValue(rs.getString("PAGE_RANK") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 8).setCellValue(rs.getString("DESCRIPTION") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 9).setCellValue(rs.getString("DEMO_URL") + "");
                } catch (Exception sd) {
                }
                try {
                    row.createCell((int) 10).setCellValue(rs.getString("CATEGORY_ID") + "");
                } catch (Exception sd) {
                }
                try {
                    row.createCell((int) 11).setCellValue(rs.getString("CATEGORY_NAME") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 12).setCellValue(rs.getString("CATEGORY_URL") + "");
                } catch (Exception sd) {
                }

                try {
                    row.createCell((int) 13).setCellValue(rs.getString("ISCRAWLED") + "");
                } catch (Exception sd) {
                }

                k++;
            }

            try {

                String filename = "data.csv";
                System.out.println("Directory is created!");
                FileOutputStream fileOut = new FileOutputStream(filename);
                hwb.write(fileOut);
                fileOut.close();
                System.out.println("Your excel file has been generated!");
            } catch (IOException iOException) {
            }

        } catch (Exception aaa) {
        }

    }
}
