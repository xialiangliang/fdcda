package com.keyou.fdcda.api.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zzq on 2017/8/28.
 */
public class ExcelUtil {
    public static List<String[]> readExcel(String fileUrl, String sheetName, int startRow, int cells) throws Exception {
        //save(file);// 保存到上传资料里
        List<String[]> valueList = new ArrayList<String[]>();
        String ExtensionName = getExtensionName(fileUrl);

        if (ExtensionName.equalsIgnoreCase("xls")) {
            valueList = readExcel2003(fileUrl, sheetName, startRow, cells);
        } else if (ExtensionName.equalsIgnoreCase("xlsx")) {
            valueList = readExcel2007(fileUrl, sheetName, startRow, cells);
        }
        // 删除缓存文件
        return valueList;
    }

    /**
     * 文件操作 获取文件扩展名
     *
     * @Author: zzq
     * @param filename
     *            文件名称包含扩展名
     * @return
     */
    private static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 读取97-2003格式
     *
     * @param filePath
     *            文件路径
     * @param startRow 从第startRow+1行开始读
     * @throws java.io.IOException
     */
    private static List<String[]> readExcel2003(String filePath, String sheetName, int startRow, int cells) throws Exception {
        return readExcel2003(filePath, sheetName, startRow, 0, cells);
    }


    /**
     * 读取97-2003格式
     *
     * @param filePath
     *            文件路径
     * @param startRow 从第startRow+1行开始读
     * @param endRow 读取截止行数
     * @throws java.io.IOException
     */
    private static List<String[]> readExcel2003(String filePath, String sheetName, int startRow, int endRow, int cells) throws Exception {
        // 返回结果集
        List<String[]> valueList = new ArrayList<String[]>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            HSSFWorkbook wookbook = new HSSFWorkbook(fis); // 创建对Excel工作簿文件的引用
            HSSFSheet sheet = wookbook.getSheet(sheetName); // 根据sheet名字获取sheet
            int rows = sheet.getPhysicalNumberOfRows(); // 获取到Excel文件中的所有行数­
            if (endRow > startRow && rows > endRow) {
                rows = endRow;
            }
            /*Map<Integer, String> keys = new HashMap<Integer, String>();
            //int cells = 0;
            // 遍历行­（第1行 表头） 准备Map里的key
            HSSFRow firstRow = sheet.getRow(0);
            if (firstRow != null) {
                // 获取到Excel文件中的所有的列
                cells = firstRow.getPhysicalNumberOfCells();
                // 遍历列
                for (int j = 0; j < cells; j++) {
                    // 获取到列的值­
                    try {
                        HSSFCell cell = firstRow.getCell(j);
                        String cellValue = getCellValue(cell);
                        keys.put(j, cellValue);
                    } catch (Exception e) {
            
                    }
                }
            }*/
            // 遍历行­（从第二行开始）
            for (int i = startRow; i < rows; i++) {
                // 读取左上端单元格(从第二行开始)
                HSSFRow row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    // 准备当前行 所储存值的map
                    boolean isValidRow = false;
                    String[] rowVal = new String[cells];
                    // 遍历列
                    for (int j = 0; j < cells; j++) {
                        // 获取到列的值­
                        try {
                            HSSFCell cell = row.getCell(j);
                            String cellValue = getCellValue(cell);
                            rowVal[j] = cellValue;
                            if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
                                isValidRow = true;
                            }
                        } catch (Exception e) {

                        }
                    }
                    // 第I行所有的列数据读取完毕，放入valuelist
                    if (isValidRow) {
                        valueList.add(rowVal);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            fis.close();
        }
        return valueList;
    }

    /**
     * 读取2007-2013格式
     *
     * @param filePath
     *            文件路径
     *  @param sheetName
     * @return
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws OpenXML4JException
     * @throws java.io.IOException
     */
    private static List<String[]> readExcel2007(String filePath, String sheetName, int startRow, int cells) throws Exception {
        return XLSXCovertCSVReader.readerExcel(filePath, sheetName, cells);
    }


    private static String getCellValue(HSSFCell cell) {
        DecimalFormat df = new DecimalFormat();
        df.setGroupingUsed(false);
        String cellValue = null;
        if (cell == null)
            return null;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    break;
                }
                cellValue = df.format(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                cellValue = null;
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                cellValue = String.valueOf(cell.getErrorCellValue());
                break;
        }
        if (cellValue != null && cellValue.trim().length() <= 0) {
            cellValue = null;
        }
        return cellValue;
    }
}
