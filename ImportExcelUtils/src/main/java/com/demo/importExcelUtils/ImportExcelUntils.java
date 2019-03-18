package com.demo.importExcelUtils;

import com.xiaoleilu.hutool.util.NumberUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: admin
 * @create: 2019/3/18
 * @update: 14:54
 * @version: V1.0
 * @detail: 导入excel工具类
 **/
public class ImportExcelUntils {

    /**
     * 拼装单个obj
     *
     * @param clazz
     * @param row
     * @return
     * @throws Exception
     */
    private static <T> T dataObj(Class<T> clazz, HSSFRow row, List<String> fields) throws Exception {
        //容器
        Map<String, Object> map = new HashMap();

        //注意excel表格字段顺序要和obj字段顺序对齐 （如果有多余字段请另作特殊下标对应处理）
        for (int j = 0; j < fields.size(); j++) {
            map.put(fields.get(j), getVal(row.getCell(j)));
        }
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) throws Exception {

        //装载流
        POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
        HSSFWorkbook hw = new HSSFWorkbook(fs);

        //获取第一个sheet页
        HSSFSheet sheet = hw.getSheetAt(0);

        //容器
        List<T> ret = new ArrayList();

        // 字段行
        List<String> fields = new ArrayList<String>();
        HSSFRow fieldsRow = sheet.getRow(1);
        int n = 0;
        HSSFCell cell = fieldsRow.getCell(0);
        if (cell != null) {
            String val = getVal(fieldsRow.getCell(0));
            while (!isBlank(val)) {
                fields.add(getVal(fieldsRow.getCell(n)));
                cell = fieldsRow.getCell(++n);
                if (cell == null) {
                    break;
                } else {
                    val = getVal(fieldsRow.getCell(n));
                }
            }
            //遍历行 从下标2（第3行）开始（去除标题行和字段行）
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    //装载obj
                    ret.add(dataObj(clazz, row, fields));
                }
            }
        }

        return ret;
    }

    /**
     * 处理val（暂时只处理string和number，可以自己添加自己需要的val类型）
     *
     * @param hssfCell
     * @return
     */
    public static String getVal(HSSFCell hssfCell) {
        if (hssfCell == null){
            return null;
        }else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            return hssfCell.getStringCellValue();
        } else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return hssfCell.getStringCellValue();
        }
    }

    private static boolean isBlank(CharSequence str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for(int i = 0; i < length; ++i) {
                if (!NumberUtil.isBlankChar(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
