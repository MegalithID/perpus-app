
import java.io.File;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Asus
 */
public class FormAnggota extends javax.swing.JFrame {

    /**
     * Creates new form FormAnggota
     */
    JasperReport JasRep;
    JasperPrint Jaspri;
    Map param = new HashMap();
    JasperDesign JasDes;
    
   public FormAnggota() {
        initComponents();
        getdata();
        IDotomatis();
        gettingkat();
        namatingkat();
        getjurusan();
        namajurusan();
        getkelas();
        
        edit.setEnabled(false);
        bdelete.setEnabled(false);
    }
    
    private void getdata() {
        Connection kon = koneksi.koneksiDB();
        Object header[] = {"ID ANGGOTA", "NIS", "NAMA", "JK", "TINGKAT","JURUSAN", "KELAS", "NO HP", "STATUS"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tabelanggota.setModel(data);
        String sql_data = "SELECT * FROM anggota";
        try {
            Statement st = kon.createStatement();
            ResultSet rs = st.executeQuery(sql_data);
            while (rs.next()) {
                String d1 = rs.getString(1);
                String d2 = rs.getString(2);
                String d3 = rs.getString(3);
                String d4 = rs.getString(4);
                String d5 = rs.getString(5);
                String d6 = rs.getString(6);
                String d7 = rs.getString(7);
                String d8 = rs.getString(8);
                String d9 = rs.getString(9);

                
                String d[] = {d1, d2, d3, d4, d5, d6, d7, d8, d9};
                data.addRow(d);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void gettingkat()
    {
        try
        {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String sql_tingkat = "SELECT * FROM tingkat";
            ResultSet rs = st.executeQuery(sql_tingkat);
            while(rs.next())
            {
                tingkat.addItem(rs.getString("id"));
            }
            rs.close();
        }
        catch (Exception e)
        {
        
        }
    }
    
    public void namatingkat()
    {
         try
        {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String sql_tingkat = "SELECT tingkat FROM tingkat WHERE id='"+tingkat.getSelectedItem() + "'";
            ResultSet rs = st.executeQuery(sql_tingkat);
            while(rs.next())
            {
                NTINGKAT.setText(rs.getString("tingkat"));
            }
            rs.close();
        }
        catch (Exception e)
        {
        
        }
        
    }
    
    public void getjurusan()
    {
        try
        {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String sql_jurusan = "SELECT id_jurusan FROM jurusan";
            ResultSet rs = st.executeQuery(sql_jurusan);
            while(rs.next())
            {
                jurusan.addItem(rs.getString("id_jurusan"));
            }
            rs.close();
        }
        catch (Exception e)
        {
        
        }
    }
    
    public void namajurusan()
    {
         try
        {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String sql_tingkat = "SELECT jurusan FROM jurusan WHERE id_jurusan='"+jurusan.getSelectedItem() + "'";
            ResultSet rs = st.executeQuery(sql_tingkat);
            while(rs.next())
            {
                NJURUSAN.setText(rs.getString("jurusan"));
            }
            rs.close();
        }
        catch (Exception e)
        {
        
        }
        
    }
    
        public void getkelas()
    {
        try
        {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String sql_kelas = "SELECT id_kelas FROM kelas";
            ResultSet rs = st.executeQuery(sql_kelas);
            while(rs.next())
            {
                kelas.addItem(rs.getString("id_kelas"));
            }
            rs.close();
        }
        catch (Exception e)
        {
        
        }
    }
        
    private void clear(){
    nis.setText("");
    nama.setText("");
    no_hp.setText("");
    pria.setSelected(rootPaneCheckingEnabled);
    tingkat.setSelectedItem(1);
    jurusan.setSelectedItem("FAR");
    kelas.setSelectedItem(1);
    status.setSelectedItem("AKTIF");
    
    
    }
    
    public void update()
    {
        try
        {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String jk="";
             if(pria.isSelected())
            {
              jk=pria.getText();
            }  
            else
            {
                jk=perempuan.getText();
            }
             
             String sql_update="UPDATE anggota SET nis='"+nis.getText()
             +"',nama='"+nama.getText()
             +"',jk='"+jk
             +"',nama='"+nama.getText() 
             +"',id_tingkat='"+tingkat.getSelectedItem()
             +"',id_jurusan='"+tingkat.getSelectedItem()   
             +"',id_kelas='"+kelas.getSelectedItem()
             +"',nope='"+no_hp.getText()
             +"',status='"+status.getSelectedItem()
             +"'WHERE id_anggota='"+id.getText()+"'";
             
             st.execute(sql_update);
             JOptionPane.showMessageDialog(null, "Data berhasil di update!");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void delete()
    {
        try
        {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String sql_delete="DELETE from anggota WHERE "
                    +"id_anggota='"+id.getText()+"'";
            st.executeUpdate(sql_delete);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
    }
    
    
    
    
    
    private void IDotomatis() {
        try {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String sql_id = "SELECT * FROM anggota ORDER BY id_anggota DESC";
            ResultSet rs = st.executeQuery(sql_id);
            if (rs.next()) {
                String id_anggota = rs.getString("id_anggota").substring(1);
                String AN = "" + (Integer.parseInt(id_anggota) + 1);
                String No1 = "";
                switch (AN.length()) {
                    case 1 -> No1 = "0000";
                    case 2 -> No1 = "000";
                    case 3 -> No1 = "00";
                    case 4 -> No1 = "0";
                    default -> No1 = "";
                }
                id.setText("A" + No1 + AN);
            } else {
                id.setText("A00001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error generating ID: " + e.getMessage());
        }
    }
    
    private void input_data()
    {
        try
        {
            Connection kon = koneksi.koneksiDB();
            Statement st = kon.createStatement();
            String jk="";
            if(pria.isSelected())
            {
              jk=pria.getText();
            }  
            else
            {
                jk=perempuan.getText();
            }
            
            String sql="INSERT INTO anggota values('"+id.getText()
                    +"','"+nis.getText()
                    +"','"+nama.getText()
                    +"','"+jk
                    +"','"+tingkat.getSelectedItem()
                    +"','"+jurusan.getSelectedItem()
                    +"','"+kelas.getSelectedItem()
                    +"','"+no_hp.getText()
                    +"','"+status.getSelectedItem()
                    +"')";
            st.execute(sql);
            JOptionPane.showMessageDialog(null, "data anggota berhasil dimasukkan");
                                                    
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        
    
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jk = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        nis = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        pria = new javax.swing.JRadioButton();
        perempuan = new javax.swing.JRadioButton();
        tingkat = new javax.swing.JComboBox<>();
        jurusan = new javax.swing.JComboBox<>();
        status = new javax.swing.JComboBox<>();
        no_hp = new javax.swing.JTextField();
        NTINGKAT = new javax.swing.JTextField();
        NJURUSAN = new javax.swing.JTextField();
        laporan = new javax.swing.JButton();
        edit = new javax.swing.JButton();
        bdelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelanggota = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        kelas = new javax.swing.JComboBox<>();
        input1 = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        logout = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Form Pengelolaan Data Anggota");

        jLabel2.setText("ID ANGGOTA");

        jLabel3.setText("NIS");

        jLabel4.setText("NAMA ANGGOTA");

        jLabel5.setText("KELAMIN");

        jLabel6.setText("TINGKAT");

        jLabel7.setText("JURUSAN");

        jLabel9.setText("NO HP");

        jLabel10.setText("STATUS");

        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });

        nis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nisActionPerformed(evt);
            }
        });

        jk.add(pria);
        pria.setText("Pria");

        jk.add(perempuan);
        perempuan.setText("Perempuan");

        tingkat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tingkatMouseClicked(evt);
            }
        });
        tingkat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tingkatActionPerformed(evt);
            }
        });

        jurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jurusanActionPerformed(evt);
            }
        });

        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AKTIF", "TIDAK AKTIF" }));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });

        no_hp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                no_hpActionPerformed(evt);
            }
        });

        NTINGKAT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NTINGKATActionPerformed(evt);
            }
        });

        laporan.setText("Laporan data anggota");
        laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanActionPerformed(evt);
            }
        });

        edit.setText("EDIT");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        bdelete.setText("DELETE");
        bdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdeleteActionPerformed(evt);
            }
        });

        tabelanggota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelanggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelanggotaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelanggota);

        jLabel8.setText("KELAS");

        kelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kelasActionPerformed(evt);
            }
        });

        input1.setText("INPUT");
        input1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input1ActionPerformed(evt);
            }
        });

        jMenu3.setText("File");

        logout.setText("LogOut");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jMenu3.add(logout);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nis, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(36, 36, 36)
                                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel9))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(no_hp, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(NJURUSAN, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(tingkat, 0, 1, Short.MAX_VALUE)
                                                .addComponent(pria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(perempuan))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGap(48, 48, 48)
                                                    .addComponent(NTINGKAT, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(edit)
                                    .addGap(18, 18, 18)
                                    .addComponent(bdelete)
                                    .addGap(61, 61, 61)))
                            .addComponent(jLabel8))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(laporan)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(367, 367, 367)
                        .addComponent(jLabel1)))
                .addGap(28, 28, 28))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(131, 131, 131)
                    .addComponent(input1)
                    .addContainerGap(871, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(nis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(pria)
                            .addComponent(perempuan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tingkat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NTINGKAT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NJURUSAN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(no_hp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(edit)
                            .addComponent(bdelete))
                        .addContainerGap(59, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(laporan)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(374, Short.MAX_VALUE)
                    .addComponent(input1)
                    .addGap(46, 46, 46)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
        new pegawai().show();
        this.dispose();
        
    }//GEN-LAST:event_logoutActionPerformed

    private void laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanActionPerformed
        // TODO add your handling code here:
        try
        {
            File file = new File("src\\report1.jrxml");
            JasDes=JRXmlLoader.load(file);
            param.clear();
            JasRep=JasperCompileManager.compileReport(JasDes);
            Jaspri=JasperFillManager.fillReport(JasRep, param,koneksi.koneksiDB());
            JasperViewer.viewReport(Jaspri,false);
        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
    }//GEN-LAST:event_laporanActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        // TODO add your handling code here:
        
        
        update();
        clear();
        getdata();
        IDotomatis();
        
        laporan.setEnabled(true);
        edit.setEnabled(false);
        bdelete.setEnabled(false);
    }//GEN-LAST:event_editActionPerformed

    private void bdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdeleteActionPerformed
        // TODO add your handling code here:
        int delete = JOptionPane.showOptionDialog(this,
                "Apakah data akan dihapus?hapus?",
                "Hapus data",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,null,null);
        if(delete==JOptionPane.YES_OPTION)        
        delete();
        clear();
        getdata();
        IDotomatis();
        
        laporan.setEnabled(true);
        edit.setEnabled(false);
        bdelete.setEnabled(false);
        
    }//GEN-LAST:event_bdeleteActionPerformed

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed

    private void nisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nisActionPerformed

    private void tabelanggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelanggotaMouseClicked
        // TODO add your handling code here:
        int bar=tabelanggota.getSelectedRow();
        String a=tabelanggota.getValueAt(bar,0).toString();
        String b=tabelanggota.getValueAt(bar,1).toString();
        String c=tabelanggota.getValueAt(bar,2).toString();
        String d=tabelanggota.getValueAt(bar,3).toString();
        String e=tabelanggota.getValueAt(bar,4).toString();
        String f=tabelanggota.getValueAt(bar,5).toString();
        String g=tabelanggota.getValueAt(bar,6).toString();
        String h=tabelanggota.getValueAt(bar,7).toString();
        String i=tabelanggota.getValueAt(bar,8).toString();
        
        id.setText(a);
        nis.setText(b);
        nama.setText(c);
        
        if("PRIA".equals(d))
        {
            pria.setSelected(true);
        }else{
            perempuan.setSelected(true);
        }
        tingkat.setSelectedItem(e);
        jurusan.setSelectedItem(f);
        no_hp.setText(h);
        
        if("AKTIF".equals(i))
        {
            status.setSelectedItem(i);
        }
        else
        {
            status.setSelectedItem(i);
        }
        
        laporan.setEnabled(false);
        edit.setEnabled(true);
        bdelete.setEnabled(true);
    }//GEN-LAST:event_tabelanggotaMouseClicked

    private void tingkatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tingkatMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tingkatMouseClicked

    private void tingkatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tingkatActionPerformed
        // TODO add your handling code here:
        namatingkat();
    }//GEN-LAST:event_tingkatActionPerformed

    private void jurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jurusanActionPerformed
        // TODO add your handling code here:
        namajurusan();
    }//GEN-LAST:event_jurusanActionPerformed

    private void no_hpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_no_hpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_no_hpActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void NTINGKATActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NTINGKATActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NTINGKATActionPerformed

    private void kelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasActionPerformed

    private void input1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input1ActionPerformed
        // TODO add your handling code here:
        input_data();
        getdata();
        clear();
    }//GEN-LAST:event_input1ActionPerformed

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
            java.util.logging.Logger.getLogger(FormAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAnggota.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAnggota().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField NJURUSAN;
    private javax.swing.JTextField NTINGKAT;
    private javax.swing.JButton bdelete;
    private javax.swing.JButton edit;
    private javax.swing.JTextField id;
    private javax.swing.JButton input1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.ButtonGroup jk;
    private javax.swing.JComboBox<String> jurusan;
    private javax.swing.JComboBox<String> kelas;
    private javax.swing.JButton laporan;
    private javax.swing.JMenuItem logout;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField nis;
    private javax.swing.JTextField no_hp;
    private javax.swing.JRadioButton perempuan;
    private javax.swing.JRadioButton pria;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JTable tabelanggota;
    private javax.swing.JComboBox<String> tingkat;
    // End of variables declaration//GEN-END:variables
}
