package Tampilan;

import Koneksi.DBConnect;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mahasiswa {
    private JPanel Main;
    private JTextField IDmahasiswa;
    private JTextField namaMahasiswa;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JTable tblMahasiswa;
    private JTextField txtId;
    Connection con = DBConnect.Connect();
    PreparedStatement pst;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Data Mahasiswa");
        frame.setContentPane(new Mahasiswa().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Mahasiswa() {
        table_load_address();
        txtId.setVisible(false);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = IDmahasiswa.getText();
                String NamaMahasiswa = namaMahasiswa.getText();

                try {
                    pst = con.prepareStatement("insert into SistemInformasiAkademik.mahasiswa(nim, nama)values(?,?)");
                    pst.setInt(1, Integer.parseInt(nim));
                    pst.setString(2, NamaMahasiswa);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Berhasil memasukkan data mahasiswa");

                    emptyAllForm();
                    table_load_address();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tblMahasiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                onSelectMahasiswa();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                try{
                    pst = con.prepareStatement("delete from SistemInformasiAkademik.mahasiswa where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Berhasil menghapus data mahasiswa");

                    table_load_address();
                    emptyAllForm();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                String nim = IDmahasiswa.getText();
                String NamaMahasiswa = namaMahasiswa.getText();

                try {
                    pst = con.prepareStatement("update SistemInformasiAkademik.mahasiswa set nim = ?," +
                            "nama = ?" +
                            "where id = ?");

                    pst.setInt(1, Integer.parseInt(nim));
                    pst.setString(2, NamaMahasiswa);
                    pst.setString(3, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Berhasil melakukan update data mahasiswa");

                    table_load_address();
                    emptyAllForm();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void emptyAllForm() {
        IDmahasiswa.setText("");
        namaMahasiswa.setText("");
    }

    private void table_load_address() {
        try{
            pst = con.prepareStatement("select * from SistemInformasiAkademik.mahasiswa");
            ResultSet rs = pst.executeQuery();
            tblMahasiswa.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void onSelectMahasiswa() {
        int row = tblMahasiswa.getSelectedRow();
        String id = tblMahasiswa.getModel().getValueAt(row, 0).toString();
        String nim = tblMahasiswa.getModel().getValueAt(row, 1).toString();
        String NamaMahasiswa = tblMahasiswa.getModel().getValueAt(row, 2).toString();

        txtId.setText(id);
        IDmahasiswa.setText(nim);
        namaMahasiswa.setText(NamaMahasiswa);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
