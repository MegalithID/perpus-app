
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Asus
 */
public class FormPinjam extends javax.swing.JFrame {

    /**
     * Creates new form FormPinjam
     */
  public FormPinjam() {
        initComponents();
        getdata();
        getid();
        getdatapinjam();
        getkdbuku();
        
        // Tambahkan ActionListener ke JComboBox id_anggota
        id_anggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getnama();
            }
        });

        // Tambahkan ActionListener ke JComboBox kd_buku
        kd_buku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getbuku();
            }
        });
    }
    
    private void getdata() {
        Connection kon = koneksi.koneksiDB();
        Object header[] = {"Kode Buku","Nama Buku","Nama Pengarang","Penerbit","Tahun Terbit","Status"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tabelbuku.setModel(data);
        String sql_data = "SELECT * FROM buku";
        
        try (Statement st = kon.createStatement();
             ResultSet rs = st.executeQuery(sql_data)) {
            while (rs.next()) {
                String d1 = rs.getString(1);
                String d2 = rs.getString(2);
                String d3 = rs.getString(3);
                String d4 = rs.getString(4);
                String d5 = rs.getString(5);
                String d6 = rs.getString(6);
                               
                String d[] = {d1, d2, d3, d4, d5,d6};
                data.addRow(d);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
    private void getdatapinjam() {
        Connection kon = koneksi.koneksiDB();
        Object header[] = {"ID ANGGOTA", "NAMA ANGGOTA", "KODE BUKU", "NAMA BUKU", "TANGGAL PINJAM", "TANGGAL KEMBALI"};
        DefaultTableModel data = new DefaultTableModel(null, header);
        tabelpinjam.setModel(data);
        String sql_data = "SELECT id_anggota, nama_anggota, kd_buku, nama_buku, tanggal_pinjam, tanggal_kembali FROM peminjam";
        
        try (Statement st = kon.createStatement();
             ResultSet rs = st.executeQuery(sql_data)) {
            while (rs.next()) {
                String d1 = rs.getString("id_anggota");
                String d2 = rs.getString("nama_anggota");
                String d3 = rs.getString("kd_buku");
                String d4 = rs.getString("nama_buku");
                String d5 = rs.getString("tanggal_pinjam");
                String d6 = rs.getString("tanggal_kembali");
                               
                String d[] = {d1, d2, d3, d4, d5, d6};
                data.addRow(d);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
     
    public void getid() {
        try (Connection kon = koneksi.koneksiDB();
             Statement st = kon.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM anggota")) {
            
            while (rs.next()) {
                id_anggota.addItem(rs.getString("id_anggota"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
    public void getnama() {
        String selectedId = (String) id_anggota.getSelectedItem();
        if (selectedId == null) return;

        try (Connection kon = koneksi.koneksiDB();
             Statement st = kon.createStatement();
             ResultSet rs = st.executeQuery("SELECT nama FROM anggota WHERE id_anggota='" + selectedId + "'")) {
            
            if (rs.next()) {
                nama.setText("" + rs.getString("nama"));
            } else {
                nama.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getkdbuku() {
        try (Connection kon = koneksi.koneksiDB();
             Statement st = kon.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM buku")) {
            
            while (rs.next()) {
                kd_buku.addItem(rs.getString("kd_buku"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getbuku() {
        String selectedId = (String) kd_buku.getSelectedItem();
        if (selectedId == null) return;

        try (Connection kon = koneksi.koneksiDB();
             Statement st = kon.createStatement();
             ResultSet rs = st.executeQuery("SELECT nama_buku FROM buku WHERE kd_buku='" + selectedId + "'")) {
            
            if (rs.next()) {
                nama_buku.setText(rs.getString("nama_buku"));
            } else {
                nama_buku.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void tambahpinjam() {
      try (Connection kon = koneksi.koneksiDB();
         Statement st = kon.createStatement()) {
        
        String selectedIdAnggota = (String) id_anggota.getSelectedItem();
        String selectedKdBuku = (String) kd_buku.getSelectedItem();
        String namaAnggota = nama.getText();
        String namaBuku = nama_buku.getText();
        String tanggalPinjam = tgl_pinjam.getText();
        String tanggalKembali = tgl_kembali.getText();
        
        if (selectedIdAnggota == null || selectedKdBuku == null || namaAnggota.isEmpty() || namaBuku.isEmpty() || tanggalPinjam.isEmpty() || tanggalKembali.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pastikan semua field telah diisi.");
            return;
        }
        
        String sql_insert = "INSERT INTO peminjam (id_anggota, nama_anggota, kd_buku, nama_buku, tanggal_pinjam, tanggal_kembali) VALUES ('"
                + selectedIdAnggota + "','"
                + namaAnggota + "','"
                + selectedKdBuku + "','"
                + namaBuku + "','"
                + tanggalPinjam + "','"
                + tanggalKembali + "')";
        
        st.executeUpdate(sql_insert);
        
        String sql_update_buku = "UPDATE buku SET status = 'sedang dipinjam' WHERE kd_buku = '" + selectedKdBuku + "'";
        st.executeUpdate(sql_update_buku);
        
        JOptionPane.showMessageDialog(null, "Data peminjam berhasil dimasukkan dan status buku diperbarui.");
        getdatapinjam(); // Refresh tabel setelah mengupdate
        getdata(); // Refresh tabel buku setelah mengupdate
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
    }
    
    private void kembalikanBuku() {
    try (Connection kon = koneksi.koneksiDB();
         Statement st = kon.createStatement()) {
        
        String selectedIdAnggota = (String) id_anggota.getSelectedItem();
        String selectedKdBuku = (String) kd_buku.getSelectedItem();
        
        if (selectedIdAnggota == null || selectedKdBuku == null) {
            JOptionPane.showMessageDialog(null, "Pastikan buku yang akan dikembalikan dipilih.");
            return;
        }
        
        String sql_delete = "DELETE FROM peminjam WHERE id_anggota = '" + selectedIdAnggota + "' AND kd_buku = '" + selectedKdBuku + "'";
        int rowsAffected = st.executeUpdate(sql_delete);
        
        if (rowsAffected > 0) {
            String sql_update_buku = "UPDATE buku SET status = 'tersedia' WHERE kd_buku = '" + selectedKdBuku + "'";
            st.executeUpdate(sql_update_buku);
            
            JOptionPane.showMessageDialog(null, "Buku berhasil dikembalikan dan status diperbarui.");
            getdatapinjam(); // Refresh tabel setelah mengupdate
            getdata(); // Refresh tabel buku setelah mengupdate
        } else {
            JOptionPane.showMessageDialog(null, "Pengembalian gagal, data tidak ditemukan.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}
    
    private void clear(){
    id_anggota.setSelectedItem("");
    nama.setText("");
    kd_buku.setSelectedItem("");
    nama_buku.setText("");
    tgl_pinjam.setText("");
    tgl_kembali.setText("");
    
    }
    
       private void delete() {
        String selectedId = (String) id_anggota.getSelectedItem();
        if (selectedId == null) return;
        
        try (Connection kon = koneksi.koneksiDB();
             Statement st = kon.createStatement()) {

            String sql_delete = "DELETE FROM peminjam WHERE id_anggota='" + selectedId + "'";
            int rowsAffected = st.executeUpdate(sql_delete);

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data peminjam berhasil dihapus.");
            } else {
                JOptionPane.showMessageDialog(null, "Data peminjam tidak ditemukan.");
            }
            getdatapinjam(); // Refresh tabel setelah menghapus
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
       
    public void update() {
    try {
        Connection kon = koneksi.koneksiDB();
        Statement st = kon.createStatement();
        
        String selectedIdAnggota = (String) id_anggota.getSelectedItem();
        String selectedKdBuku = (String) kd_buku.getSelectedItem();
        String namaAnggota = nama.getText();
        String namaBuku = nama_buku.getText();
        
        if (selectedIdAnggota == null || selectedKdBuku == null || namaAnggota.isEmpty() || namaBuku.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pastikan semua field telah diisi.");
            return;
        }
        
        String sql_update = "UPDATE peminjam SET " +
                            "id_anggota = '" + selectedIdAnggota + "', " +
                            "nama_anggota = '" + namaAnggota + "', " +
                            "nama_buku = '" + namaBuku + "' " +
                            "WHERE kd_buku = '" + selectedKdBuku + "'";
        
        int rowsAffected = st.executeUpdate(sql_update);
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Data peminjam berhasil di-update!");
            getdatapinjam(); // Refresh tabel setelah mengupdate
        } else {
            JOptionPane.showMessageDialog(null, "Update gagal, data tidak ditemukan.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e);
    }
}


    // Main method if needed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormPinjam().setVisible(true);
            }
        });
    }
      
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpinjam = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelbuku = new javax.swing.JTable();
        id_anggota = new javax.swing.JComboBox<>();
        kd_buku = new javax.swing.JComboBox<>();
        tgl_pinjam = new javax.swing.JTextField();
        tgl_kembali = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        nama_buku = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        keluar = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Peminjaman Buku");

        jLabel2.setText("NIS");

        jLabel3.setText("NAMA");

        jLabel4.setText("KODE BUKU");

        jLabel5.setText("NAMA BUKU");

        jLabel6.setText("TANGGAL PINJAM");

        jLabel7.setText("TANGGAL KEMBALI");

        tabelpinjam.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelpinjam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpinjamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpinjam);

        tabelbuku.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelbuku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelbukuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelbuku);

        id_anggota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                id_anggotaMouseClicked(evt);
            }
        });
        id_anggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_anggotaActionPerformed(evt);
            }
        });

        kd_buku.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kd_bukuMouseClicked(evt);
            }
        });
        kd_buku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kd_bukuActionPerformed(evt);
            }
        });

        jLabel8.setText("TABEL DATA BUKU");

        jLabel9.setText("TABEL DATA PINJAM");

        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Edit");

        jButton4.setText("Simpan");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        keluar.setText("Keluar");
        keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarActionPerformed(evt);
            }
        });

        jButton5.setText("Kembalikan Buku");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(40, 40, 40)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(nama_buku, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(nama)
                                                .addComponent(id_anggota, 0, 177, Short.MAX_VALUE)
                                                .addComponent(kd_buku, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tgl_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tgl_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jButton4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton2)
                                            .addComponent(jButton3)))
                                    .addComponent(keluar, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(28, 28, 28))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 663, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(165, 165, 165)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5))
                .addGap(48, 48, 48))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jLabel9)
                    .addContainerGap(1098, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(id_anggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel3)
                                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4)
                                            .addComponent(kd_buku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(keluar))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton3)
                                    .addComponent(jButton4))))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(nama_buku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tgl_pinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tgl_kembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButton5)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(268, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addGap(213, 213, 213)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelpinjamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpinjamMouseClicked
         int bar=tabelpinjam.getSelectedRow();
        String a=tabelpinjam.getValueAt(bar,0).toString();
        String b=tabelpinjam.getValueAt(bar,1).toString();
        String c=tabelpinjam.getValueAt(bar,2).toString();
        String d=tabelpinjam.getValueAt(bar,3).toString();
        String e=tabelpinjam.getValueAt(bar,4).toString();
        String f=tabelpinjam.getValueAt(bar,5).toString();
        
        id_anggota.setSelectedItem(a);
        nama.setText(b);
        kd_buku.setSelectedItem(c);
        nama_buku.setText(d);
        tgl_pinjam.setText(e);
        tgl_kembali.setText(f);
    }//GEN-LAST:event_tabelpinjamMouseClicked

    private void tabelbukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelbukuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelbukuMouseClicked

    private void id_anggotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_id_anggotaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_id_anggotaMouseClicked

    private void id_anggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_anggotaActionPerformed
        // TODO add your handling code here:
        getid();
        
    }//GEN-LAST:event_id_anggotaActionPerformed

    private void kd_bukuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kd_bukuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_kd_bukuMouseClicked

    private void kd_bukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kd_bukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kd_bukuActionPerformed

    private void keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarActionPerformed
        // TODO add your handling code here:
        new pegawai().show();
        this.dispose();
    }//GEN-LAST:event_keluarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        tambahpinjam();
        getdatapinjam();
        clear();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update();
        getdatapinjam();
         clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin mengembalikan buku ini?", "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
        kembalikanBuku();
    }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> id_anggota;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> kd_buku;
    private javax.swing.JButton keluar;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField nama_buku;
    private javax.swing.JTable tabelbuku;
    private javax.swing.JTable tabelpinjam;
    private javax.swing.JTextField tgl_kembali;
    private javax.swing.JTextField tgl_pinjam;
    // End of variables declaration//GEN-END:variables
}
