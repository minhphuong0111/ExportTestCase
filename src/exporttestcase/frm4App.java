/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exporttestcase;

import function.excelFile;
import java.io.File;
import static java.lang.System.in;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author minhp
 */
public class frm4App extends javax.swing.JFrame {

    /**
     * Creates new form frm4App
     * if use for web call constructor with param is true
     */
    String recentPath = "";
    boolean isWeb = false;
    String dataPath;
    String importPath; //import test case
    List<List<String>> data = new ArrayList<List<String>>();//file data
    List<List<String>> export = new ArrayList<List<String>>();//du lieu export ra file excel
    List<List<String>> tcimport = new ArrayList<List<String>>();//file test case import vao
    List<List<String>> controls = new ArrayList<List<String>>();//file controls data
    
    excelFile objExcelFile;
    DefaultTableModel dtm;
    DefaultListModel<String> dlm;
    DefaultComboBoxModel<String> dcm;
    
    public frm4App() {
        //app
        initComponents();
        txtObject.setEnabled(false);
        objExcelFile = new excelFile();
        dtm = new DefaultTableModel();
        dlm = new DefaultListModel<>();
        Object[] title = {"Test Case", "TC", "Keyword", "Object", "ObjectPA", "Value"};
        dtm.setColumnIdentifiers(title);
        tblData.setModel(dtm);
        
        objExcelFile.readExcelFile("AppControls.xlsx");
        controls = objExcelFile.getData();
        for(List<String> value : controls)
        {
            dlm.addElement(value.get(0));
        }
        lstControls.setModel(dlm);
    }
    
    
    
    private void btnDeleteAction()
    {
        if(tblData.getSelectedRow() > -1 && tblData.getSelectedRowCount() == 1)
        {
            int irow = tblData.getSelectedRow();
            dtm.removeRow(irow);
            
        }
    }
    
    private void btnUpdateAction()
    {
        int lstSelected = lstControls.getSelectedIndex();
        int cbxSelected = 0;
        int rowSelected = tblData.getSelectedRow();
        //remove _index
        
        
        
        if(!txtTestcase.getText().equals(""))
        {
            //dtm.addRow(new Object[]{txtTestcase.getText(),generateTC(),"","","",""});
            dtm.setValueAt(txtTestcase.getText(),rowSelected,0);
            //dtm.setValueAt("", rowSelected, 1);
            dtm.setValueAt("", rowSelected, 2);
            dtm.setValueAt("", rowSelected, 3);
            dtm.setValueAt("", rowSelected, 4);
            dtm.setValueAt("", rowSelected, 5);
        }
        else
        {
            if(isWeb)
            {
                if(txtObject.getText().equals("") && txtValue.getText().equals("") && cbxValue.getSelectedIndex() < 1)
                {
                    //du lieu rong, chi co action(controls)
                    dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                }
                else
                {
                    if(!txtValue.getText().equals(""))
                    {
                        //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",txtValue.getText()});
                        dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                        dtm.setValueAt(txtObject.getText(),rowSelected,3);
                        dtm.setValueAt("XPATH",rowSelected,4);
                        dtm.setValueAt(txtValue.getText(),rowSelected,5);
                    }
                    else if(cbxValue.getSelectedIndex()>0)
                    {
                        //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",cbxValue.getSelectedIndex()});
                        dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                        dtm.setValueAt(txtObject.getText(),rowSelected,3);
                        dtm.setValueAt("XPATH",rowSelected,4);
                        dtm.setValueAt(cbxValue.getSelectedItem(),rowSelected,5);
                        
                    }
                    else{
                        dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                        dtm.setValueAt("",rowSelected,3);
                        dtm.setValueAt("",rowSelected,4);
                        dtm.setValueAt("",rowSelected,5);
                    }
                }
            }
            else
            {
                //desk app
                if(!txtValue.getText().equals(""))
                {
                    //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",txtValue.getText()});
                    dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                    //dtm.setValueAt(txtObject.getText(),rowSelected,3);
                    //dtm.setValueAt("XPATH",rowSelected,4);
                    dtm.setValueAt(txtValue.getText(),rowSelected,5);
                }
                else if(cbxValue.getSelectedIndex()>0)
                {
                    //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",cbxValue.getSelectedIndex()});
                    dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                    //dtm.setValueAt(txtObject.getText(),rowSelected,3);
                    //dtm.setValueAt("XPATH",rowSelected,4);
                    dtm.setValueAt(cbxValue.getSelectedItem(),rowSelected,5);
                    
                }
                else{
                    dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                    //dtm.setValueAt(txtObject.getText(),rowSelected,3);
                    //dtm.setValueAt("XPATH",rowSelected,4);
                    dtm.setValueAt("",rowSelected,5);
                }
            }
        }
    }
    
    private void btnInsertAction()
    {
        if(tblData.getSelectedRow() > -1 && tblData.getSelectedRowCount() == 1)
        {
            int irow = tblData.getSelectedRow()+1;
            List<String> ltemp  = new ArrayList<>();
                    
            if(!txtTestcase.getText().equals(""))
            {
                dtm.insertRow(irow,new Object[]{txtTestcase.getText(),generateTC(),"","","",""});
            }
            else
            {
                if(isWeb)
                {
                    if(txtObject.getText().equals("") && txtValue.getText().equals("") && cbxValue.getSelectedIndex() < 1)
                    {
                        //object type rong
                        dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});

                    }
                    else
                    {
                        if(!txtValue.getText().equals(""))
                        {
                            dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",txtValue.getText()});
                        }
                        else if(cbxValue.getSelectedIndex()>0)
                        {
                            dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",cbxValue.getSelectedItem()});
                            
                        }
                        else{
                            dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});
                        }
                    }
                }
                else
                {
                    //desk app
                    if(!txtValue.getText().equals(""))
                    {
                        dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",txtValue.getText()});
                    }
                    else if(cbxValue.getSelectedIndex()>0)
                    {
                        dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",cbxValue.getSelectedItem()});
                        
                    }
                    else{
                        dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});
                    }
                }
            }
            int tablesize = tblData.getRowCount();
            int irun = 0;
            
            
        }
    }
    
    private void btnAddAction()
    {
        int lstSelected = lstControls.getSelectedIndex();
        int cbxSelected = 0;
        
        if(!txtTestcase.getText().equals(""))
        {
            dtm.addRow(new Object[]{txtTestcase.getText(),generateTC(),"","","",""});
        }
        else
        {
            if(isWeb)
            {
                if(txtObject.getText().equals("") && txtValue.getText().equals("") && cbxValue.getSelectedIndex() < 1)
                {
                    //object type rong
                    dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});
                }
                else
                {
                    if(!txtValue.getText().equals(""))
                    {
                        dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",txtValue.getText()});
                    }
                    else if(cbxValue.getSelectedIndex()>0)
                    {
                        dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",cbxValue.getSelectedItem()});
                        
                    }
                    else{
                        dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});
                    }
                }
            }
            else
            {
                //desk app
                if(!txtValue.getText().equals(""))
                {
                    dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",txtValue.getText()});
                }
                else if(cbxValue.getSelectedIndex()>0)
                {
                    dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",cbxValue.getSelectedItem()});                    
                }
                else{
                    dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});
                }
            }
        }
        txtValue.setText("");
        txtTestcase.setText("");
        if(cbxValue.getSelectedIndex() != 0)
        {
            int t = cbxValue.getSelectedIndex();  
            //JOptionPane.showMessageDialog(this, "t= "+t+" data.size() = "+data.size());
            if(t < data.get(0).size()) cbxValue.setSelectedIndex(t+1);
        }
    }
    
    
    public frm4App(boolean web) {//set boolean = true
        initComponents();
        isWeb = web;
        objExcelFile = new excelFile();
        dtm = new DefaultTableModel();
        dlm = new DefaultListModel<>();
        Object[] title = {"Test Case", "TC", "Keyword", "Object", "Object Type", "Value"};
        
        dtm.setColumnIdentifiers(title);
        tblData.setModel(dtm);
        
        objExcelFile.readExcelFile("Webcontrols.xlsx");
        controls = objExcelFile.getData();
        for(List<String> value : controls)
        {
            dlm.addElement(value.get(0));
        }
        lstControls.setModel(dlm);
    }
    
    int TCtemp = 0;
    private String generateTC()
    {
        TCtemp++;
        String TC = "TC";
        if(TCtemp >9 && TCtemp <100) TC += "0"+TCtemp;
        else if(TCtemp >99 && TCtemp <1000) TC += ""+TCtemp;
        else TC += "00"+TCtemp;
        return TC;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnData = new javax.swing.JButton();
        txtFilename = new javax.swing.JLabel();
        txtFileTC = new javax.swing.JLabel();
        btnTC = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstControls = new javax.swing.JList<>();
        btnImage = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtValue = new javax.swing.JTextField();
        cbxValue = new javax.swing.JComboBox<>();
        btnInsert = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtObject = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtTestcase = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 30)); // NOI18N
        jLabel1.setText("GENERATE TESTCASE FOR DESKTOP APPLICATION");
        jPanel1.add(jLabel1);

        jLabel2.setText("Data:");

        btnData.setText("Browser...");
        btnData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDataActionPerformed(evt);
            }
        });

        btnTC.setText("Browser...");
        btnTC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTCActionPerformed(evt);
            }
        });

        jLabel3.setText("TestCase:");

        jLabel4.setText("List Controls");

        lstControls.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lstControlsKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(lstControls);

        btnImage.setText("Browser...");
        btnImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImageActionPerformed(evt);
            }
        });

        jLabel5.setText("Image:");

        jLabel6.setText("Value");

        txtValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtValueKeyPressed(evt);
            }
        });

        cbxValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbxValueKeyPressed(evt);
            }
        });

        btnInsert.setText("Insert Row");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete Row");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        btnDelete.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDeleteKeyPressed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAddKeyPressed(evt);
            }
        });

        btnExport.setText("Export Excel");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        jLabel7.setText("Object:");

        jLabel8.setText("Test Case:");

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        btnUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnUpdateKeyPressed(evt);
            }
        });

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFilename, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtFileTC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnImage)
                                            .addComponent(txtObject)
                                            .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbxValue, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(327, 327, 327))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtTestcase, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnInsert)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnExport)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(btnTC))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(btnData))
                            .addComponent(txtFilename, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(txtFileTC, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtTestcase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(btnImage))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtObject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnInsert)
                            .addComponent(btnDelete)
                            .addComponent(btnAdd)
                            .addComponent(btnExport)
                            .addComponent(btnUpdate)
                            .addComponent(jButton1))))
                .addContainerGap())
        );

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblData);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDataActionPerformed
        //clear list
        data.clear();
        dcm = new DefaultComboBoxModel<>();
        //if(dcm.getSize() > 0)  dcm.removeAllElements();
        
        
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Excel file", ".xlsx");
        chooser.addChoosableFileFilter(filter);
        if(!recentPath.equals(""))
            chooser.setCurrentDirectory(new File(recentPath));
        int result = chooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            dataPath = selectedFile.getPath();
            recentPath = selectedFile.getPath();
            txtFilename.setText(selectedFile.getName());
            objExcelFile.readExcelFile(dataPath);
            data = objExcelFile.getData();
            //load du lieu len combobox
            int cbxsize = data.get(0).size();
            //JOptionPane.showMessageDialog(this, ""+cbxsize);
            
            dcm.addElement("--NONE--");
            for(int i = 0; i<cbxsize; i++)
            {
                //dcm.addElement(data.get(0).get(i));
                dcm.addElement("Column "+ (i+1));
            }
            //data.remove(0);
            cbxValue.setModel(dcm);
        }
    }//GEN-LAST:event_btnDataActionPerformed

    private void btnTCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTCActionPerformed
        tcimport.clear();
        if (dtm.getRowCount() > 0) {
        for (int i = dtm.getRowCount() - 1; i > -1; i--) {
            dtm.removeRow(i);
            }
        }

        
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Excel file", ".xlsx");
        chooser.addChoosableFileFilter(filter);
        if(!recentPath.equals(""))
            chooser.setCurrentDirectory(new File(recentPath));
        int result = chooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            importPath = selectedFile.getPath();
            recentPath = selectedFile.getPath();
            txtFileTC.setText(selectedFile.getName());
            objExcelFile.readExcelFile(importPath);
            tcimport = objExcelFile.getData();
            //load du lieu len table
            for(List<String> value : tcimport)
            {
                dtm.addRow(value.toArray());
            }
        }
    }//GEN-LAST:event_btnTCActionPerformed

    private void btnImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImageActionPerformed
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Excel file", ".xlsx");
        chooser.addChoosableFileFilter(filter);
        if(!recentPath.equals(""))
            chooser.setCurrentDirectory(new File(recentPath));
        int result = chooser.showOpenDialog(this);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            recentPath = selectedFile.getPath();
            txtValue.setText(selectedFile.getPath());
        }
    }//GEN-LAST:event_btnImageActionPerformed
    
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
/*
        int lstSelected = lstControls.getSelectedIndex();
        int cbxSelected = 0;
        if(txtValue.getText().equals(""))
        {
            if(cbxValue.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(this,"Vui lòng chọn value");
            }
            else cbxSelected = cbxValue.getSelectedIndex();
        }
        
        if(!txtTestcase.getText().equals(""))
        {
            dtm.addRow(new Object[]{txtTestcase.getText(),generateTC(),"","","",""});
        }
        else
        {
            if(isWeb)
            {
                if(txtObject.getText().equals("") && txtValue.getText().equals("") && cbxValue.getSelectedIndex() < 1)
                {
                    //object type rong
                    dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});
                }
                else
                {
                    if(!txtValue.getText().equals(""))
                    {
                        dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",txtValue.getText()});
                    }
                    else if(cbxValue.getSelectedIndex()>0)
                    {
                        dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",cbxValue.getSelectedIndex()});
                        _index.add(dtm.getRowCount()-1);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn value");
                    }
                }
            }
            else
            {
                //desk app
                if(!txtValue.getText().equals(""))
                {
                    dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",txtValue.getText()});
                }
                else if(cbxValue.getSelectedIndex()>0)
                {
                    dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",cbxValue.getSelectedIndex()});
                    _index.add(dtm.getRowCount()-1);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn value");
                }
            }
        }
        txtValue.setText("");
        txtTestcase.setText("");
        if(cbxValue.getSelectedIndex() != 0)
        {
            int t = cbxValue.getSelectedIndex();                
            if(t+1 <= data.size()-1)
            cbxValue.setSelectedIndex(t+1);
        }
        */
        btnAddAction();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
/*        int lstSelected = lstControls.getSelectedIndex();
        int cbxSelected = 0;
        int rowSelected = tblData.getSelectedRow();
        //remove _index
        for(int i = 0; i< _index.size(); i++)
        {
            if(_index.get(i) == rowSelected) _index.remove(i);
        }
        
        if(txtValue.getText().equals(""))
        {
            if(cbxValue.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(this,"Vui lòng chọn value");
            }
            else cbxSelected = cbxValue.getSelectedIndex();
        }
        
        if(!txtTestcase.getText().equals(""))
        {
            dtm.addRow(new Object[]{txtTestcase.getText(),generateTC(),"","","",""});
            dtm.setValueAt(txtTestcase.getText(),rowSelected,0);
            //dtm.setValueAt("", rowSelected, 1);
            dtm.setValueAt("", rowSelected, 2);
            dtm.setValueAt("", rowSelected, 3);
            dtm.setValueAt("", rowSelected, 4);
            dtm.setValueAt("", rowSelected, 5);
        }
        else
        {
            if(isWeb)
            {
                if(txtObject.getText().equals("") && txtValue.getText().equals("") && cbxValue.getSelectedIndex() < 1)
                {
                    //object type rong
                    //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});
                    dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                }
                else
                {
                    if(!txtValue.getText().equals(""))
                    {
                        //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",txtValue.getText()});
                        dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                        dtm.setValueAt(txtObject.getText(),rowSelected,3);
                        dtm.setValueAt("XPATH",rowSelected,4);
                        dtm.setValueAt(txtValue.getText(),rowSelected,5);
                    }
                    else if(cbxValue.getSelectedIndex()>0)
                    {
                        //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",cbxValue.getSelectedIndex()});
                        dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                        dtm.setValueAt(txtObject.getText(),rowSelected,3);
                        dtm.setValueAt("XPATH",rowSelected,4);
                        dtm.setValueAt(cbxValue.getSelectedIndex(),rowSelected,5);
                        _index.add(dtm.getRowCount()-1);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn value");
                    }
                }
            }
            else
            {
                //desk app
                if(!txtValue.getText().equals(""))
                {
                    //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",txtValue.getText()});
                    dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                    //dtm.setValueAt(txtObject.getText(),rowSelected,3);
                    //dtm.setValueAt("XPATH",rowSelected,4);
                    dtm.setValueAt(txtValue.getText(),rowSelected,5);
                }
                else if(cbxValue.getSelectedIndex()>0)
                {
                    //dtm.addRow(new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",cbxValue.getSelectedIndex()});
                    dtm.setValueAt(lstControls.getSelectedValue(),rowSelected,2);
                    //dtm.setValueAt(txtObject.getText(),rowSelected,3);
                    //dtm.setValueAt("XPATH",rowSelected,4);
                    dtm.setValueAt(cbxValue.getSelectedIndex(),rowSelected,5);
                    _index.add(dtm.getRowCount()-1);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn value");
                }
            }
        }
        */
        btnUpdateAction();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        txtObject.setText("");
        txtValue.setText("");
        if(tblData.getSelectedRow() >= 0 && tblData.getSelectedRowCount() == 1)
        {
            int rowSelected = tblData.getSelectedRow();
        //remove _index
        boolean flag = false;
            
            lstControls.setSelectedValue(tblData.getValueAt(tblData.getSelectedRow(), 2),true);
            if(flag)
            {
                if(isWeb) txtObject.setText(tblData.getValueAt(tblData.getSelectedRow(), 3).toString());
                cbxValue.setSelectedIndex(Integer.parseInt(tblData.getValueAt(tblData.getSelectedRow(), 5).toString()));
            }
            else
            {
                if(isWeb) txtObject.setText(tblData.getValueAt(tblData.getSelectedRow(), 3).toString());
                txtValue.setText(tblData.getValueAt(tblData.getSelectedRow(),5).toString());
            }
            
        }
    }//GEN-LAST:event_tblDataMouseClicked

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        int tablerow = tblData.getRowCount();
        List<List<String>> ltemp = new ArrayList<List<String>>();
        for(int i = 0; i<tablerow; i++)
        {
            List<String> temp = new ArrayList<>();
            
            temp.add((tblData.getValueAt(i, 0) == null ? "": tblData.getValueAt(i, 0).toString()));
            temp.add((tblData.getValueAt(i, 1) == null ? "": tblData.getValueAt(i, 1).toString()));
            temp.add((tblData.getValueAt(i, 2) == null ? "": tblData.getValueAt(i, 2).toString()));
            temp.add((tblData.getValueAt(i, 3) == null ? "": tblData.getValueAt(i, 3).toString()));
            temp.add((tblData.getValueAt(i, 4) == null ? "": tblData.getValueAt(i, 4).toString()));
            temp.add((tblData.getValueAt(i, 5) == null ? "": tblData.getValueAt(i, 5).toString()));
            export.add(temp);
            ltemp.add(temp);
            
            //ket thuc gan gia tri cho export va ltemp
        }
        objExcelFile.createExcelFile(export,"template.xlsx");
        
        
        //lap qua tung dong trong du lieu va bo qua dong dau tien
        for(int loop = 1; loop < data.size()-1; loop++)
        {
            //kiem tra neu la lan lap dau tien thi set lai du lieu cho export
            if(loop == 1)
            {//lan lap dau tien -- set lai du lieu cho export
                //lap qua tung dong du lieu cua export
                for(int irun = 0; irun < export.size(); irun++)
                {
                    if(export.get(irun).get(5).length() > 5)//kiem tra do dai > 5 de cat chuoi kiem tra
                    {
                        if(export.get(irun).get(5).substring(0, 6).equals("Column"))
                        {//value chua column
                            try
                           {//neu convert chuoi duoc thi kiem tra gia tri voi data.size
                               int value = Integer.parseInt(export.get(irun).get(5).substring(7))-1;
                               if(value <= data.get(loop).size())
                               {
                                   //du lieu hop le
                                   //luu ra mang tam roi set lai gia tri
                                   List<String> t = new ArrayList<>();
                                   t.add(export.get(irun).get(0));
                                   t.add(export.get(irun).get(1));
                                   t.add(export.get(irun).get(2));
                                   t.add(export.get(irun).get(3));
                                   t.add(export.get(irun).get(4));
                                   t.add(data.get(loop).get(value));
                                   export.set(irun,t);
                                   
                               }
                                
                            }
                            catch(Exception ex){}
                        }
                    }
                }
                
            }
            else
            {//them du lieu cho export
                for(int iadd = 0; iadd <ltemp.size(); iadd++)
                {
                    if(ltemp.get(iadd).get(5).length()>5)
                    {
                        //JOptionPane.showMessageDialog(this, ""+ltemp.get(iadd).get(5).substring(0, 6));
                        if(ltemp.get(iadd).get(5).substring(0, 6).equals("Column"))
                        {
                            try{
                                
                                int value = Integer.parseInt(ltemp.get(iadd).get(5).substring(7))-1;
                                
                                if(value <= data.get(loop).size())
                                {
                                    //du lieu hop le
                                    List<String> t = new ArrayList<>();
                                    t.add(ltemp.get(iadd).get(0));
                                    t.add(ltemp.get(iadd).get(1));
                                    t.add(ltemp.get(iadd).get(2));
                                    t.add(ltemp.get(iadd).get(3));
                                    t.add(ltemp.get(iadd).get(4));
                                    t.add(data.get(loop).get(value));
                                    export.add(t);
                                }
                            }
                            catch(Exception ex){}
                        }
                        else
                        {
                            //add new row with data is ltemp
                            export.add(ltemp.get(iadd));
                        }
                    }
                    else
                    {
                        export.add(ltemp.get(iadd));
                    }
                }
            }
            
        }
        
    
        
        List<String> header = new ArrayList<>();
        header.add("TestCase");
        header.add("TC");
        header.add("Keywork");
        header.add("Object");
        header.add("ObjectPA");
        header.add("Value");
        export.add(0, header);
        objExcelFile.createExcelFile(export,"TestCase.xlsx");
        export.clear();
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
    /*    if(tblData.getSelectedRow() > -1 && tblData.getSelectedRowCount() == 1)
        {
            int irow = tblData.getSelectedRow()+1;
            List<String> ltemp  = new ArrayList<>();
                    
            if(!txtTestcase.getText().equals(""))
            {
                dtm.insertRow(irow,new Object[]{txtTestcase.getText(),generateTC(),"","","",""});
            }
            else
            {
                if(isWeb)
                {
                    if(txtObject.getText().equals("") && txtValue.getText().equals("") && cbxValue.getSelectedIndex() < 1)
                    {
                        //object type rong
                        dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",""});

                    }
                    else
                    {
                        if(!txtValue.getText().equals(""))
                        {
                            dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",txtValue.getText()});
                        }
                        else if(cbxValue.getSelectedIndex()>0)
                        {
                            dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),txtObject.getText(),"XPATH",cbxValue.getSelectedIndex()});
                            _index.add(irow);
                        }
                        else{
                            JOptionPane.showMessageDialog(this, "Vui lòng chọn value");
                        }
                    }
                }
                else
                {
                    //desk app
                    if(!txtValue.getText().equals(""))
                    {
                        dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",txtValue.getText()});
                    }
                    else if(cbxValue.getSelectedIndex()>0)
                    {
                        dtm.insertRow(irow,new Object[]{"",generateTC(),lstControls.getSelectedValue(),"","",cbxValue.getSelectedIndex()});
                        _index.add(irow);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn value");
                    }
                }
            }
            int tablesize = tblData.getRowCount();
            for(int i = irow; i<tablesize; i++)
            {
                for(int j = 0; j < _index.size(); j++)
                {
                    if(i == _index.get(j))
                    {
                        _index.set(j, j+1);
                    }
                }
            }
            
        }
        */
        btnInsertAction();
        
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        /* if(tblData.getSelectedRow() > -1 && tblData.getSelectedRowCount() == 1)
        {
            int irow = tblData.getSelectedRow();
            dtm.removeRow(irow);
            for(int i = irow; i< tblData.getRowCount(); i++)
            {
                for (int j = 0; j < _index.size(); j++)
                {
                    if(i == _index.get(j))
                    {
                        _index.set(j, j-1);
                    }
                }
            }
        }*/
        btnDeleteAction();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        frmMain x = new frmMain();
        x.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lstControlsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstControlsKeyPressed
        if(lstControls.getSelectedIndex()> -1)
        {
            if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
            {
                txtValue.requestFocus();
            }
        }
    }//GEN-LAST:event_lstControlsKeyPressed

    private void cbxValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbxValueKeyPressed
        if(evt.isControlDown() && evt.getKeyCode() == java.awt.event.KeyEvent.VK_A)
        {
            btnAddAction();
            lstControls.requestFocus();
            lstControls.setSelectedIndex(0);
        }
    }//GEN-LAST:event_cbxValueKeyPressed

    private void btnAddKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAddKeyPressed
        if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
        {
            btnAddAction();
        }
    }//GEN-LAST:event_btnAddKeyPressed

    private void btnDeleteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDeleteKeyPressed
        if(evt.getKeyCode() ==  java.awt.event.KeyEvent.VK_ENTER)
        {
            btnDeleteAction();
        }
    }//GEN-LAST:event_btnDeleteKeyPressed

    private void btnUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnUpdateKeyPressed
        if(evt.getKeyCode() ==  java.awt.event.KeyEvent.VK_ENTER)
        {
            btnUpdateAction();
        }
    }//GEN-LAST:event_btnUpdateKeyPressed

    private void txtValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValueKeyPressed
        if(evt.isControlDown() && evt.getKeyCode() == java.awt.event.KeyEvent.VK_A)
        {
            btnAddAction();
            lstControls.requestFocus();
            lstControls.setSelectedIndex(0);
        }
    }//GEN-LAST:event_txtValueKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm4App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm4App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm4App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm4App.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm4App().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnData;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnImage;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnTC;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbxValue;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lstControls;
    private javax.swing.JTable tblData;
    private javax.swing.JLabel txtFileTC;
    private javax.swing.JLabel txtFilename;
    private javax.swing.JTextField txtObject;
    private javax.swing.JTextField txtTestcase;
    private javax.swing.JTextField txtValue;
    // End of variables declaration//GEN-END:variables
}
