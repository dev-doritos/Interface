package com.cubea.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.event.EventListenerList;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class Basic {

	private JFrame frmRsmLead;
	private String FILEPATH = "";
	
	/*
	 * 동적 생성 요소
	 * */
	private JLabel VERIFIABLE_LABEL;
	private JButton VERIFICATION_BUTTON;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Basic window = new Basic();
					window.frmRsmLead.setExtendedState(JFrame.MAXIMIZED_BOTH);	// 전체화면 모드 설정
					window.frmRsmLead.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Basic() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRsmLead = new JFrame();
		frmRsmLead.setBackground(Color.DARK_GRAY);
		frmRsmLead.setTitle("RSM Lead");
		frmRsmLead.setBounds(100, 100, 882, 462);
		frmRsmLead.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// label 설정
		JLabel label = DefaultComponentFactory.getInstance().createTitle("인터페이스 파일이 있는 디렉터리를 설정해 주세요.");
		label.setFont(new Font("굴림", Font.PLAIN, 14));
		label.setBounds(0, 4, 434, 47);
		frmRsmLead.getContentPane().add(label);
		
		// dirsearch 버튼 설정
		JButton btnDirSearch = new JButton("폴더찾기");
		btnDirSearch.setBounds(333, 10, 89, 37);
		
		/*
		 * 버튼 eventListener 설정
		 * 디렉토리를 설정 한 후, 해당 디렉토리 내 검증 가능한 갯수 라벨 생성
		 * 검증 가능한 갯수가 1개 이상 일 시, 검증 버튼 생성
		 * */
		btnDirSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// 디렉토리 선택 library
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showDialog(fileChooser, null);
				
				// 사용자가 선택한 디렉토리
				File SELECT_DIRECTORY = fileChooser.getSelectedFile();
				
				// 디렉토리 가 null 일 시, 이벤트 종료
				if(SELECT_DIRECTORY == null) {
					return;
				}
				
				int VERIFIABLE_COUNT = 0;
				String RESULT_MSG = "";
				FILEPATH = SELECT_DIRECTORY.toString();
				
				try{
					// 디렉토리 내 파일 갯수 확인
					VERIFIABLE_COUNT = getFileCount(SELECT_DIRECTORY);
					RESULT_MSG = "[" + FILEPATH + "] 검증 가능 파일 갯수 {" + VERIFIABLE_COUNT + "}";
				}
				catch(Exception exception) {
					exception.printStackTrace();
					System.out.println(exception.getMessage());
					RESULT_MSG = exception.getMessage();
				}
				
				// 버튼이 이미 생성되었는지 확인.
				if(VERIFIABLE_LABEL == null) {
					VERIFIABLE_LABEL = DefaultComponentFactory.getInstance().createTitle(RESULT_MSG);
					VERIFIABLE_LABEL.setFont(new Font("굴림", Font.PLAIN, 12));
					VERIFIABLE_LABEL.setBounds(0, 61, 1920, 31);
				}
				else {
					VERIFIABLE_LABEL.setText(RESULT_MSG);
				}
				frmRsmLead.getContentPane().add(VERIFIABLE_LABEL);
				
				// 검증 가능한 파일이 존재한다면,
				if(VERIFIABLE_COUNT > 0) {
					if(VERIFICATION_BUTTON == null){
						VERIFICATION_BUTTON = new JButton();
						VERIFICATION_BUTTON.setText("검증 시작");
						VERIFICATION_BUTTON.setBounds(0, 85, 89, 37);
					}
					else {
						VERIFICATION_BUTTON.setText("검증 시작");
					}
					frmRsmLead.getContentPane().add(VERIFICATION_BUTTON);
					
					/*
					 * 버튼 eventListener 설정
					 * 
					 * */
					VERIFICATION_BUTTON.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							verification();
						}
					});
				}
				
				// 프레임 초기화
				frmRsmLead.revalidate();
				frmRsmLead.repaint();
			}
		});
		frmRsmLead.getContentPane().setLayout(null);
		frmRsmLead.getContentPane().add(btnDirSearch);
	}
	
	public int getFileCount(File select_directory) throws Exception {
		if(select_directory == null) {
			throw new NullPointerException("directory is null.");
		}
		
		if(!select_directory.isDirectory()) {
			throw new UnknownError("Please set only the directory.");
		}
		
		File[] files = select_directory.listFiles();
		if(files == null || files.length == 0) {
			return 0;
		}
		
		int COUNT = 0;
		for(File row : files) {
			String filename = row.getName();
			if(filename.startsWith("HS01")) {
				COUNT ++;
			}
		}
		
		System.out.println("call By getFileCount {COUNT} " + COUNT);
		
		return COUNT;
	}
	
	public void verification() {
		if("".equals(FILEPATH)){
			
		}
	}
}
