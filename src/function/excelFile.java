/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author minhp
 */
public class excelFile {
    
    private List<List<String>> excelData;
    public List<List<String>> getData()
    {
        return excelData;
    }
    public void setData(List<List<String>> data)
    {
        excelData = data;
    }
    
    public void createExcelFile(List<List<String>> data, String fileName)
    {
        String filePath = "fileName";
        File file = new File(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("KeywordFramework");
        
        try{
            int rownum = 0;
	        
            for(int k = 0 ; k < data.size(); k++)
            {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = {data.get(k).get(0),data.get(k).get(1),data.get(k).get(2),data.get(k).get(3),data.get(k).get(4),data.get(k).get(5)};
                int cellnum = 0;
                for (Object obj : objArr)
                {
                    Cell cell = row.createCell(cellnum++);
                    if(obj instanceof String)
                        {cell.setCellValue((String)obj);
                            System.out.println((String)obj);
                        }
                    else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
                }
            }
            FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            //System.out.println("javahabitExcelDemo.xlsx Successfully created");
            JOptionPane.showMessageDialog(null,"Tạo file thành công");
        }
        catch(Exception ex){JOptionPane.showMessageDialog(null, "Lỗi tạo file");}
    }
    
    public void readExcelFile(String filePath)
    {
        excelData = new ArrayList<List<String>>();
        File file = new File(filePath);
        if(!file.canRead())
        {
            JOptionPane.showMessageDialog(null, "Không thể mở file\nVui lòng kiểm tra lại");
        }
        else
        {
            //Tiến hành đọc file
            try {
        	    FileInputStream fis = new FileInputStream(file);
        	    XSSFWorkbook workbook = new XSSFWorkbook(fis);
        	    XSSFSheet sheet = workbook.getSheetAt(0);
        	    
        	    int rowCount = sheet.getLastRowNum();
        	    for(Row row : sheet)
                    {
                        List<String> _data = new ArrayList<>();
                        for(Cell cell : row)
                        {
                            cell.setCellType(cell.CELL_TYPE_STRING);
                            _data.add(cell.getStringCellValue());
                        }
                        excelData.add(_data);
                    }
        	    fis.close();
                } catch (FileNotFoundException e) {
        	    JOptionPane.showMessageDialog(null, "File not found");
        	} catch (IOException e) {
        	    JOptionPane.showMessageDialog(null, "Lỗi đọc file IOException");
        	}
        }
    }
    
}
