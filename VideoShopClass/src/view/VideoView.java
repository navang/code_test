package	 view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.dao.VideoModel;
import model.vo.Video;


public class VideoView extends JPanel 
{	
	//	member field
	JTextField	tfVideoNum, tfVideoTitle, tfVideoDirector, tfVideoActor;
	JComboBox	comVideoJanre;
	JTextArea	taVideoContent;

	JCheckBox	cbMultiInsert;
	JTextField	tfInsertCount;

	JButton		bVideoInsert, bVideoModify, bVideoDelete;

	JComboBox	comVideoSearch;
	JTextField	tfVideoSearch;
	
	JTable		tableVideo;
	
	VideoTableModel tbModelVideo;
	
	//**************
	// 비지니스 로직 = 모델 클래스 변수 선언
	VideoModel db;

	//##############################################
	//	constructor method
	public VideoView(){
		addLayout(); 	// 화면설계
	
		initStyle();
		eventProc();
		
		connectDB();	// DB연결
	}
	
	public void connectDB(){	// DB연결
		try {
			db = new VideoModel();
		} catch (Exception e) {
			System.out.println("드라이버 로딩 실패 ");
		}
	}
	
	public void eventProc(){
		ButtonEventHandler hdlr = new ButtonEventHandler();
		bVideoInsert.addActionListener(hdlr);
		bVideoDelete.addActionListener(hdlr);
		bVideoModify.addActionListener(hdlr);
		tfVideoSearch.addActionListener(hdlr);
		
		cbMultiInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				tfInsertCount.setEditable(cbMultiInsert.isSelected());  // setEditable(true) 클릭한 값이 트루면  
				
			}
		});
		
		// jtable 비디오 검색 클릭했을때  
		tableVideo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
					int col = 0;
					int row = tableVideo.getSelectedRow();
					int vNum = (Integer)tableVideo.getValueAt(row, col);
					JOptionPane.showMessageDialog(null, vNum);
					
					// 1. 선택한 비디오번호를 모델단의 selectByPK() 호출시 인자로 보내기 
					// 2. 넘겨받은 Video에서 해당 값들 화면 출력하기 
					Video vo = db.selectByPK(vNum);
					tfVideoNum.setText(String.valueOf(vo.getVideoNo()));
				
			}
		});
	}		
	
	// 버튼 이벤트 핸들러 만들기
	class ButtonEventHandler implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			Object o = ev.getSource();
			
			if(o==bVideoInsert){  
				registVideo();					// 비디오 등록
			}
			else if(o==bVideoModify){  
				modifyVideo();					// 비디오 정보 수정
			}
			else if(o==bVideoDelete){  
				deleteVideo();					// 비디오 정보 삭제
			}
			else if(o==tfVideoSearch){
				searchVideo();					// 비디오 검색
			}
		}
	}
	
	// 입고 클릭시  - 비디오 정보 등록
	public void registVideo(){
		 // JOptionPane.showMessageDialog(null, "입고");
		
		// 1.선책 값들 얻어오기 6개의 값 
		Video vo = new Video();
		
		
		// 2. 모델단의 메소드 1번값들을 전송 
		vo.setGenre((String)comVideoJanre.getSelectedItem());
		vo.setVideoName(tfVideoTitle.getText());
		vo.setDirector(tfVideoDirector.getText());
		vo.setActor(tfVideoActor.getText());
		vo.setExp(taVideoContent.getText());
		int count = Integer.parseInt(tfInsertCount.getText());

		try {
			db.insertVideo(vo, count);
		}catch (Exception e) {
			
		}
	}
	
	public void initStyle(){   // 입력하지 못하게 만듬.
		tfVideoNum.setEditable(false);  // 편집만 못하게 만듬 -- SetEditable // 기능 상실   -- SetEnabled 
		tfInsertCount.setEditable(false);		
		tfInsertCount.setHorizontalAlignment(JTextField.RIGHT);
	}
	
	// 수정 클릭시 - 비디오 정보 수정
	public void modifyVideo(){
		JOptionPane.showMessageDialog(null, "수정");
	}
	
	// 삭제 클릭시 - 비디오 정보 삭제
	public void deleteVideo(){
		
		JOptionPane.showMessageDialog(null, "삭제");
	}
	
	// 비디오현황검색
	public void searchVideo(){
			//JOptionPane.showMessageDialog(null, "검색");
			int sel = comVideoSearch.getSelectedIndex();
			String word = tfVideoSearch.getText();
			
			try {
				// 검색결과를 화면 JTable 의 내용을 담당하는 TableModel 안에 data 지정 
				tbModelVideo.data = db.searchVideo(sel, word);
				tbModelVideo.fireTableDataChanged();
			}
			catch (Exception e) {
				System.out.println("검색실패:" + e.getMessage());
			}
			
		}
		
	//  화면설계 메소드
	public void addLayout(){
		//멤버변수의 객체 생성
		tfVideoNum = new JTextField();
		tfVideoTitle = new JTextField();
		tfVideoDirector = new JTextField();
		tfVideoActor = new JTextField();
		
		String []cbJanreStr = {"멜로","엑션","스릴","코미디"};
		comVideoJanre = new JComboBox(cbJanreStr);
		taVideoContent = new JTextArea();
		
		cbMultiInsert = new JCheckBox("다중입고");
		tfInsertCount = new JTextField("1",5);
	
		bVideoInsert = new JButton("입고");
		bVideoModify = new JButton("수정");
		bVideoDelete = new JButton("삭제");
		
		String []cbVideoSearch = {"제목","감독"};
		comVideoSearch = new JComboBox(cbVideoSearch);
		tfVideoSearch = new JTextField(15);
		
		tbModelVideo = new VideoTableModel();
		tableVideo = new JTable(tbModelVideo);

		//*********************************************************************
		// 화면 구성
		
		//왼쪽 영역
		JPanel p_west = new JPanel();
		p_west.setBorder(new TitledBorder("메인"));
		p_west.setLayout(new BorderLayout());
			
			// 왼쪽 - 메인영역
		
			// 아래 버튼영역
			JPanel p_west_c = new JPanel();
			p_west_c.setLayout(new BorderLayout());
			
					// 왼쪽 메인 - 중앙
					JPanel p_west_c_c = new JPanel();
					p_west_c_c.setBorder(new TitledBorder("비디오 정보 입력"));
						
						//비디오 정보 입력 부분
						JPanel p_w_c_c_1 = new JPanel();

							
								
						p_w_c_c_1.setLayout(new GridLayout(5,2));
						p_w_c_c_1.add(new JLabel("비디오번호"));
							p_w_c_c_1.add(tfVideoNum);
						p_w_c_c_1.add(new JLabel("장르"));
							p_w_c_c_1.add(comVideoJanre);
						p_w_c_c_1.add(new JLabel("제목"));
							p_w_c_c_1.add(tfVideoTitle);
						p_w_c_c_1.add(new JLabel("감독"));
							p_w_c_c_1.add(tfVideoDirector);
						p_w_c_c_1.add(new JLabel("배우"));
							p_w_c_c_1.add(tfVideoActor);
						
								
								
						// 비디오 설명 입력 부분
						JPanel p_w_c_c_2 = new JPanel();
						p_w_c_c_2.setLayout(new BorderLayout());
						p_w_c_c_2.add(new JLabel("설명"), BorderLayout.WEST);
						p_w_c_c_2.add(taVideoContent, BorderLayout.CENTER);
						
					p_west_c_c.setLayout(new GridLayout(2,1));
					p_west_c_c.add(p_w_c_c_1);
					p_west_c_c.add(p_w_c_c_2);
						
				p_west_c.add(p_west_c_c, BorderLayout.CENTER);
					
					// 왼쪽 메인 - 아래
					JPanel p_west_c_s = new JPanel();
					p_west_c_s.setBorder(new TitledBorder("다중입고시 선택하시오"));
					p_west_c_s.add(cbMultiInsert);
					p_west_c_s.add(tfInsertCount);
					p_west_c_s.add(new JLabel("개"));
					
						
				p_west_c.add(p_west_c_s, BorderLayout.SOUTH);
				
			p_west.add(p_west_c, BorderLayout.CENTER);
			
				// 왼쪽 버튼 영역 
					JPanel p_west_s = new JPanel();
				
					p_west_s.setLayout(new GridLayout(1,3));
					p_west_s.add(bVideoInsert);
					p_west_s.add(bVideoModify);
					p_west_s.add(bVideoDelete);
				p_west.add(p_west_s, BorderLayout.SOUTH);
			
		
		//오른쪽 영역
		JPanel p_east = new JPanel();
		p_east.setBorder(new TitledBorder("비디오검색"));
		p_east.setLayout(new BorderLayout());
			
			JPanel p_east_n = new JPanel();
			
				p_east_n.add(comVideoSearch);
				p_east_n.add(tfVideoSearch);
			
			
			p_east.add(p_east_n, BorderLayout.NORTH);
			p_east.add(new JScrollPane(tableVideo), BorderLayout.CENTER );
	
			
		//전체에 붙임
		setLayout(new GridLayout(1,2));
		add(p_west);
		add(p_east);
		
		
		
		
		
	}
	
	//화면에 테이블 붙이는 메소드

	class VideoTableModel extends AbstractTableModel { 
		  
		ArrayList data = new ArrayList();
		String [] columnNames = {"비디오번호","제목","장르","감독","배우"};

		//=============================================================
		// 1. 기본적인 TabelModel  만들기
		// 아래 세 함수는 TabelModel 인터페이스의 추상함수인데
		// AbstractTabelModel에서 구현되지 않았기에...
		// 반드시 사용자 구현 필수!!!!

		    public int getColumnCount() { 
		        return columnNames.length; 
		    } 
		     
		    public int getRowCount() { 
		        return data.size(); 
		    } 

		    public Object getValueAt(int row, int col) { 
				ArrayList temp = (ArrayList)data.get( row );
		        return temp.get( col ); 
		    }
		    
		    public String getColumnName(int col){
		    	return columnNames[col];
		    }
	}
}


