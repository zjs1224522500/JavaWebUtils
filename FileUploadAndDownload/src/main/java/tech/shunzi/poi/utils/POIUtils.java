package tech.shunzi.poi.utils;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * ClassName: POIUtils <br/>
 * <br/>
 * Description: Utils for export excel.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class POIUtils
{

    final static Integer MAX_ITEM_QUANITY = 5000;

    /**
     * Title: createWorkbookWithMultiSheet <br/>
     * <br/>
     * Description: create workbook with multi-sheet
     * 
     * @param workbook
     * @param sheets
     * @return HSSFWorkbook
     * @see
     * @since
     */
    public static HSSFWorkbook createWorkbookWithMultiSheet(HSSFWorkbook workbook, List<POIExcelSheetInfo> sheets)
    {
        sheets.stream().forEach(singleSheet -> {
            HSSFSheet sheet = workbook.createSheet(singleSheet.getSheetName());
            createSheet(sheet, singleSheet, getDefaultHeaderStyle(workbook), getDefaultBodyStyle(workbook));
        });
        return workbook;
    }

    /**
     * Title: getDefaultHeaderStyle <br/>
     * <br/>
     * Description: get default header cell style and font.
     * 
     * @param workbook
     * @return HSSFCellStyle
     * @see
     * @since
     */
    private static HSSFCellStyle getDefaultHeaderStyle(HSSFWorkbook workbook)
    {
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontName("Calibri");
        headerFont.setFontHeightInPoints((short) 20);
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

    /**
     * Title: getDefaultBodyStyle <br/>
     * <br/>
     * Description: get default body cell style and font.
     * 
     * @param workbook
     * @return HSSFCellStyle
     * @see
     * @since
     */
    private static HSSFCellStyle getDefaultBodyStyle(HSSFWorkbook workbook)
    {
        HSSFCellStyle bodyStyle = workbook.createCellStyle();
        HSSFFont bodyFont = workbook.createFont();
        bodyFont.setFontName("Calibri");
        bodyFont.setFontHeightInPoints((short) 12);
        bodyStyle.setFont(bodyFont);
        return bodyStyle;
    }

    /**
     * Title: createSheet <br/>
     * <br/>
     * Description: create single sheet with sheet info
     * 
     * @param sheet
     * @param sheetInfo
     * @param headerStyle
     * @param bodyStyle
     * @return HSSFSheet
     * @see
     * @since
     */
    public static HSSFSheet createSheet(HSSFSheet sheet, POIExcelSheetInfo sheetInfo, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle)
    {
        List<String[]> bodyValue = sheetInfo.getSheetBody();
        if (CollectionUtils.isEmpty(bodyValue))
        {
            return null;
        }

        if (MAX_ITEM_QUANITY.intValue() < bodyValue.size())
        {
            throw new RuntimeException();
        }

        String[] headerValue = sheetInfo.getSheetHeader();
        HSSFRow headerRow = sheet.createRow(0);

        for (int i = 0; i < headerValue.length; i++)
        {
            HSSFCell cell = headerRow.createCell(i);
            cell.setCellValue(headerValue[i]);
            cell.setCellStyle(headerStyle);
        }

        for (int rowNum = 1; rowNum < bodyValue.size() + 1; rowNum++)
        {
            HSSFRow bodyRow = sheet.createRow(rowNum);
            String[] singleBodyValue = bodyValue.get(rowNum - 1);
            for (int columnNo = 0; columnNo < singleBodyValue.length; columnNo++)
            {
                HSSFCell cell = bodyRow.createCell(columnNo);
                cell.setCellValue(singleBodyValue[columnNo]);
                cell.setCellStyle(bodyStyle);
                sheet.autoSizeColumn(columnNo);
            }
        }
        return sheet;
    }

    public class POIExcelSheetInfo
    {

        private String sheetName;

        private String[] sheetHeader;

        private List<String[]> sheetBody;

        public String getSheetName()
        {

            return sheetName;
        }

        public void setSheetName(String sheetName)
        {
            this.sheetName = sheetName;
        }

        public String[] getSheetHeader()
        {

            return sheetHeader;
        }

        public void setSheetHeader(String[] sheetHeader)
        {
            this.sheetHeader = sheetHeader;
        }

        public List<String[]> getSheetBody()
        {

            return sheetBody;
        }

        public void setSheetBody(List<String[]> sheetBody)
        {
            this.sheetBody = sheetBody;
        }
    }

}
