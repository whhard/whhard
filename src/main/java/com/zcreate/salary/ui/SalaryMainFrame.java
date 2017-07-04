package com.zcreate.salary.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import com.zcreate.salary.pojo.SalaryInfoBean;
import com.zcreate.salary.service.SalaryDataLoadService;
import com.zcreate.salary.service.SalaryEmailSendService;
import com.zcreate.salary.util.SalaryProperties;

public class SalaryMainFrame extends JFrame {
	private static final long serialVersionUID = 2699890041272928964L;
	
	private static SalaryMainFrame instance;
	
	private JTable  table;
	private JLabel statusLabel;
	
	private JButton loadFileBtn;
	
	private JButton sendEmailBtn;
	
	private JButton pauseEmailBtn;
	
	private boolean isPause;
	
	

	public static SalaryMainFrame getDefaultMainFrame(){
		if(instance==null){
			instance=new SalaryMainFrame();
		}
		return instance;
	}
	
	public SalaryMainFrame() throws HeadlessException {
		super("贵州智诚科技工资条发送工具-V0.1");
		initGUI();
	}

	private void initGUI() {
		setSize(900, 600);
		
		URL url =getClass().getResource("/config/logo.png");
		Image img = Toolkit.getDefaultToolkit().getImage(url);
		setIconImage(img);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFont(new Font("Helvetica", Font.PLAIN, 14));
		setLayout(new BorderLayout());

		getContentPane().add(getNorthPanel(), BorderLayout.NORTH);
		getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
		getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
		
	}

	private JPanel getNorthPanel() {
		JPanel ret = new JPanel();
		ret.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		ret.setBackground(Color.lightGray);

		JLabel fileInfoLable = new JLabel("请加载文件");
		loadFileBtn = new JButton("加载");
		loadFileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				File defaultPath=new File(SalaryProperties.getSalaryProperty("excel.data.default.path"));
				
				JFileChooser jfc =defaultPath.exists()?new JFileChooser(defaultPath):new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (file != null&&file.isFile()) {
					fileInfoLable.setText(file.getAbsolutePath());
					List<SalaryInfoBean>  list=SalaryDataLoadService.getDefaultService().getSalarysInfoList(file);
					SalaryTableModel tmodel=(SalaryTableModel)table.getModel();
					tmodel.setList(list);
					table.updateUI();
				}else{
					SalaryMainFrame.getDefaultMainFrame().updateMassage("未选择任何文件");
				}
			}
		});

		ret.add(fileInfoLable);
		ret.add(loadFileBtn);
		
		sendEmailBtn = new JButton("发送邮件");
		sendEmailBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				operationDisabled();
				
				SalaryTableModel tmodel=(SalaryTableModel)table.getModel();
				List<SalaryInfoBean> list=tmodel.getList();
				if(list==null||list.size()==0){
					JOptionPane.showMessageDialog(SalaryMainFrame.getDefaultMainFrame(),"没有可发送的数据","提示",JOptionPane.ERROR_MESSAGE); 
					operationActivate();
					return;
				}
				String content=(String)JOptionPane.showInputDialog(null,"输入邮件信息：\n","邮件信息",JOptionPane.PLAIN_MESSAGE,null,null,"");
				sendEmail(list,content);
			}
		});
		ret.add(sendEmailBtn);
		
		pauseEmailBtn=new JButton("暂停");
		pauseEmailBtn.setEnabled(false);
		pauseEmailBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionevent) {
				if(!isPause){
					isPause=true;
					pauseEmailBtn.setText("继续");
				}else{
					isPause=false;
					pauseEmailBtn.setText("暂停");
				}
			}
		});
		ret.add(pauseEmailBtn);
		
		return ret;
	}

	private JScrollPane getCenterPanel() {
		JScrollPane ret=new JScrollPane();
		ret.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		ret.setBackground(Color.LIGHT_GRAY);
		
		table=new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
		
		SalaryTableModel tmodel=new SalaryTableModel();
		table.setModel(tmodel);
		
		ret.setViewportView(table);
		
		return ret;
	}

	private JPanel getSouthPanel() {
		JPanel ret = new JPanel();
		statusLabel=new JLabel("©贵州智诚科技");
		ret.add(statusLabel);
		return ret;
	}
	
	//---------------操作类方法---------------------
	public void operationDisabled(){
		loadFileBtn.setEnabled(false);
		sendEmailBtn.setEnabled(false);
		pauseEmailBtn.setEnabled(true);
	}
	
	public void operationActivate(){
		loadFileBtn.setEnabled(true);
		sendEmailBtn.setEnabled(true);
		pauseEmailBtn.setEnabled(false);
	}
	public void updateMassage(String message) {
		 new Thread(new Runnable(){
             @Override
             public void run() {
            	 statusLabel.setText(message);
            	 try {
                     TimeUnit.SECONDS.sleep(1);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }).start();
	}
	
	public void sendEmail(List<SalaryInfoBean> list,String msg){
		new Thread(new Runnable(){
            @Override
            public void run() {
				SalaryEmailSendService.getDefaultInstance().sendEmail(list,msg);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }).start();
	}

	public void updateTableRow(int count,int row, String targetUserName, String userEmail){
		new Thread(new Runnable(){
            @Override
            public void run() {
            	table.setRowSelectionInterval(row,row);
            	updateMassage((row+1)+"/"+count+"|"+targetUserName+"|"+userEmail+"|正在执行...");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }).start();
		
	}
	

	public boolean isPause() {
		return isPause;
	}

	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}

	public void setFailureRow(int theRow) {
		new Thread(new Runnable(){
            @Override
            public void run() {
				try {
					DefaultTableCellRenderer tcr = new DefaultTableCellRenderer() {
						public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,boolean hasFocus, int row, int column) {
							System.out.println(">>>theRow="+theRow);
							if(row==theRow){
								setForeground(Color.red);
							}else{
								setForeground(Color.black);
							}
							return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
						}
					};
					for (int i = 0; i < table.getColumnCount(); i++) {
						table.getColumn(table.getColumnName(i)).setCellRenderer(tcr);
					}
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }).start();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SalaryMainFrame frame = SalaryMainFrame.getDefaultMainFrame();
				frame.setVisible(true);
			}
		});
	}
}
