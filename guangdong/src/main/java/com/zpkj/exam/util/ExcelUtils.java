package com.zpkj.exam.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/22.
 */
public class ExcelUtils {

    /**
     * suffix of excel 2003
     */
    public static final String OFFICE_EXCEL_V2003_SUFFIX = "xls";
    /**
     * suffix of excel 2007
     */
    public static final String OFFICE_EXCEL_V2007_SUFFIX = "xlsx";
    /**
     * suffix of excel 2010
     */
    public static final String OFFICE_EXCEL_V2010_SUFFIX = "xlsx";

    public static final String EMPTY = "";
    public static final String DOT = ".";
    public static final String NOT_EXCEL_FILE = " is Not a Excel file!";
    public static final String PROCESSING = "Processing...";

    public static void main(String[] args) throws IOException {
        try {
            //上传excel,附件前缀
            //读取excel
            //得到list
            //生成批次号
            //记录正确数，错误数
            //循环数据
            //创建明细对象
            //校验格式
            //状态、备注、批次号
            //插入数据库
            //生成批次记录


            //附件前缀，附件
            //上传附件，批次号
            //读取压缩包
            //写文件
            //生成id
            //插入附件表

            //业务逻辑处理
            /*List<Map<Integer, String>> list = readExcel("D:/writeExcel.xls", 5);
            for (Map<Integer, String> map : list) {
            }*/
            String sheetName = "用车统计表单";
            String titleName = "用车申请数据统计表";
            String fileName = "用车申请统计表单";
            int columnNumber = 3;
            int[] columnWidth = {10, 20, 30};
            String[][] dataList = {{"001", "2015-01-01", "IT"},
                    {"002", "2015-01-02", "市场部"}, {"003", "2015-01-03", "测试"}};
            String[] columnName = {"单号", "申请时间", "申请部门"};
            ExcelUtils.ExportNoResponse(sheetName, titleName, fileName,
                    columnNumber, columnWidth, columnName, dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Check which version of The Excel file is. Throw exception if Excel file path is illegal.
     *
     * @param path the Excel file
     * @return a list that contains Students from Excel.
     * @throws IOException
     */
    public static List<Map<Integer, String>> readExcel(String path, Integer columnNum) throws IOException, IllegalArgumentException {
        if ("".equals(path)) {
            throw new IllegalArgumentException(path + " excel file path is either null or empty");
        } else {
            String suffiex = getSuffiex(path);
            if ("".equals(suffiex)) {
                throw new IllegalArgumentException(path + " suffiex is either null or empty");
            }
            if (OFFICE_EXCEL_V2003_SUFFIX.equals(suffiex)) {
                return readXls(path, columnNum);
            } else if (OFFICE_EXCEL_V2007_SUFFIX.equals(suffiex)) {
                return readXlsx(path, columnNum);
            } else if (OFFICE_EXCEL_V2010_SUFFIX.equals(suffiex)) {
                return readXlsx(path, columnNum);
            } else {
                throw new IllegalArgumentException(path + NOT_EXCEL_FILE);
            }
        }
    }

    /**
     * Read the Excel 2017 or 2010
     *
     * @param path the path of the excel file
     * @return
     * @throws IOException
     */
    public static List<Map<Integer, String>> readXlsx(String path, Integer columnNum) throws IOException {
        System.out.println(PROCESSING + path);
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        Map map = null;
        List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    map = new HashMap();
                    for (int num = 0; num < columnNum; num++) {
                        map.put(num, getValue(xssfRow.getCell(num)));
                    }
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * Read the Excel 2003
     *
     * @param path the path of the Excel
     * @return
     * @throws IOException
     */
    public static List<Map<Integer, String>> readXls(String path, Integer columnNum) throws IOException {
        System.out.println(PROCESSING + path);
        InputStream is = new FileInputStream(path);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        Map map = null;
        List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // Read the Row
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    map = new HashMap();
                    for (int num = 0; num < columnNum; num++) {
                        map.put(num, getValue(hssfRow.getCell(num)));
                    }
                    list.add(map);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("static-access")
    private static String getValue(XSSFCell xssfRow) {
        if (xssfRow == null) {
            return "";
        }
        xssfRow.setCellType(xssfRow.CELL_TYPE_STRING);
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            return String.valueOf(xssfRow.getNumericCellValue());
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    @SuppressWarnings("static-access")
    private static String getValue(HSSFCell hssfCell) {
        if (hssfCell == null) {
            return "";
        }
        hssfCell.setCellType(hssfCell.CELL_TYPE_STRING);
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    public static String getSuffiex(String path) {
        if ("".equals(path)) {
            return EMPTY;
        }
        int index = path.lastIndexOf(DOT);
        if (index == -1) {
            return EMPTY;
        }
        return path.substring(index + 1, path.length());
    }


    public static void ExportWithResponse(String sheetName, String titleName,
                                          String fileName, int columnNumber, int[] columnWidth,
                                          String[] columnName, List dataList,
                                          HttpServletResponse response) throws Exception {
        if (columnNumber == columnWidth.length && columnWidth.length == columnName.length) {
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            // sheet.setDefaultColumnWidth(15); //统一设置列宽
            for (int i = 0; i < columnNumber; i++) {
                for (int j = 0; j <= i; j++) {
                    if (i == j) {
                        sheet.setColumnWidth(i, columnWidth[j] * 256); // 单独设置每列的宽
                    }
                }
            }
            /*// 创建第0行 也就是标题
            HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(50);// 设备标题的高度
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont1.setFontName("黑体"); // 设置字体类型
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
            style2.setFont(headerFont1); // 为标题样式设置字体样式

            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    columnNumber - 1)); // 合并列标题
            cell1.setCellValue(titleName); // 设置值标题
            cell1.setCellStyle(style2); // 设置标题样式*/

            // 创建第1行 也就是表头
            HSSFRow row = sheet.createRow((int) 0);
            row.setHeightInPoints(37);// 设置表头高度

            // 第四步，创建表头单元格样式 以及表头的字体样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// 设置自动换行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont.setFontName("黑体"); // 设置字体类型
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
            style.setFont(headerFont); // 为标题样式设置字体样式

            // 第四.一步，创建表头的列
            for (int i = 0; i < columnNumber; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName[i]);
                cell.setCellStyle(style);
            }

            // 第五步，创建单元格，并设置值
            for (int i = 0; i < dataList.size(); i++) {
                row = sheet.createRow((int) i + 1);
                // 为数据内容设置特点新单元格样式1 自动换行 上下居中
                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// 设置自动换行
                zidonghuanhang.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

                // 设置边框
                zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);

                // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中
                HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
                zidonghuanhang2.setWrapText(true);// 设置自动换行
                zidonghuanhang2
                        .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式
                zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中

                // 设置边框
                zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);
                HSSFCell datacell = null;
                Map map = (Map) dataList.get(i);
                for (int j = 0; j < columnNumber; j++) {
                    datacell = row.createCell(j);
                    datacell.setCellValue((String) map.get(j));
                    datacell.setCellStyle(zidonghuanhang2);
                }
            }

            // 第六步，将文件存到浏览器设置的下载位置
            String filename = fileName + ".xls";
            response.setContentType("application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    .concat(String.valueOf(URLEncoder.encode(filename, "UTF-8"))));
            OutputStream out = response.getOutputStream();
            try {
                wb.write(out);// 将数据写出去
                String str = "导出" + fileName + "成功！";
                System.out.println(str);
            } catch (Exception e) {
                e.printStackTrace();
                String str1 = "导出" + fileName + "失败！";
                System.out.println(str1);
            } finally {
                out.close();
            }

        } else {
            System.out.println("列数目长度名称三个数组长度要一致");
        }

    }

    public static void ExportNoResponse(String sheetName, String titleName,
                                        String fileName, int columnNumber, int[] columnWidth,
                                        String[] columnName, String[][] dataList) throws Exception {
        if (columnNumber == columnWidth.length && columnWidth.length == columnName.length) {
            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            HSSFSheet sheet = wb.createSheet(sheetName);
            // sheet.setDefaultColumnWidth(15); //统一设置列宽
            for (int i = 0; i < columnNumber; i++) {
                for (int j = 0; j <= i; j++) {
                    if (i == j) {
                        sheet.setColumnWidth(i, columnWidth[j] * 256); // 单独设置每列的宽
                    }
                }
            }
            // 创建第0行 也就是标题
            /*HSSFRow row1 = sheet.createRow((int) 0);
            row1.setHeightInPoints(50);// 设备标题的高度
            // 第三步创建标题的单元格样式style2以及字体样式headerFont1
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style2.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont headerFont1 = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont1.setFontName("黑体"); // 设置字体类型
            headerFont1.setFontHeightInPoints((short) 15); // 设置字体大小
            style2.setFont(headerFont1); // 为标题样式设置字体样式

            HSSFCell cell1 = row1.createCell(0);// 创建标题第一列
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    columnNumber - 1)); // 合并第0到第17列
            cell1.setCellValue(titleName); // 设置值标题
            cell1.setCellStyle(style2); // 设置标题样式*/

            // 创建第1行 也就是表头
            HSSFRow row = sheet.createRow((int) 0);
            row.setHeightInPoints(37);// 设置表头高度

            // 第四步，创建表头单元格样式 以及表头的字体样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setWrapText(true);// 设置自动换行
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

            style.setBottomBorderColor(HSSFColor.BLACK.index);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);

            HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
            headerFont.setFontName("黑体"); // 设置字体类型
            headerFont.setFontHeightInPoints((short) 10); // 设置字体大小
            style.setFont(headerFont); // 为标题样式设置字体样式

            // 第四.一步，创建表头的列
            for (int i = 0; i < columnNumber; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(columnName[i]);
                cell.setCellStyle(style);
            }

            // 第五步，创建单元格，并设置值
            for (int i = 0; i < dataList.length; i++) {
                row = sheet.createRow((int) i + 1);
                // 为数据内容设置特点新单元格样式1 自动换行 上下居中
                HSSFCellStyle zidonghuanhang = wb.createCellStyle();
                zidonghuanhang.setWrapText(true);// 设置自动换行
                zidonghuanhang
                        .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式

                // 设置边框
                zidonghuanhang.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang.setBorderTop(HSSFCellStyle.BORDER_THIN);

                // 为数据内容设置特点新单元格样式2 自动换行 上下居中左右也居中
                HSSFCellStyle zidonghuanhang2 = wb.createCellStyle();
                zidonghuanhang2.setWrapText(true);// 设置自动换行
                zidonghuanhang2
                        .setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个上下居中格式
                zidonghuanhang2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中

                // 设置边框
                zidonghuanhang2.setBottomBorderColor(HSSFColor.BLACK.index);
                zidonghuanhang2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderRight(HSSFCellStyle.BORDER_THIN);
                zidonghuanhang2.setBorderTop(HSSFCellStyle.BORDER_THIN);
                HSSFCell datacell = null;
                for (int j = 0; j < columnNumber; j++) {
                    datacell = row.createCell(j);
                    datacell.setCellValue(dataList[i][j]);
                    datacell.setCellStyle(zidonghuanhang2);
                }
            }

            // 第六步，将文件存到指定位置
            try {
                FileOutputStream fout = new FileOutputStream("D://students.xls");
                wb.write(fout);
                String str = "导出" + fileName + "成功！";
                System.out.println(str);
                fout.close();
            } catch (Exception e) {
                e.printStackTrace();
                String str1 = "导出" + fileName + "失败！";
                System.out.println(str1);
            }
        } else {
            System.out.println("列数目长度名称三个数组长度要一致");
        }

    }
}

