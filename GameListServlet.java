package jp.co.tafs.kenshu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.tafs.kenshu.game.GameBean;
import jp.co.tafs.kenshu.game.GameSearchConditionBean;
import jp.co.tafs.kenshu.util.DBConnectInfo;

/**
 * ���C�Ŏg�p����A�Q�[���\�t�g�Ǘ��V�X�e���̈ꗗ��ʂ̑�����������邽�߂̃T�[�u���b�g�N���X�ł��B
 * 
 * �T�[�u���b�g�Ƃ́A�N���C�A���g���瑗��ꂽ�����������邽�߂̃T�[�o�[����Java�v���O�������L�q����
 * ���߂̃N���X�t�@�C���ł��B
 * 
 * �T�[�u���b�g�N���X�����ɂ́A�N���X��HttpServlet���p�����邱�Ƃ��񑩂ɂȂ��Ă��܂��B
 * 
 * �T�[�u���b�g���Atomcat�ɑ�\�����A�T�[�u���b�g�R���e�i�ƌĂ΂��T�[�o�[�\�t�g�E�F�A�ɓo�^���邱�ƂŁA
 * �T�[�u���b�g�R���e�i���AURL�ł̗v���ɉ������邽�߂ɕK�v�ȃT�[�u���b�g�N���X�𔻒f���ČĂяo���Ă���܂��B</p>
 * 
 * �T�[�u���b�g�R���e�i�́A�A�v���P�[�V�����T�[�o�[��1��ł��B
 * 
 * @author kawachi
 *
 */
public class GameListServlet extends HttpServlet {

	/**
	 * �N���C�A���g����̗v���̉񐔂��J�E���g���邽�߂̕ϐ��ł��B
	 * ���\�b�h�̊O���Ő錾�����ϐ��̒l�́A�T�[�o�[���ċN������܂ŏ����܂���B
	 * �܂��A�T�[�u���b�g�ŁA���̂悤�Ƀ��\�b�h�̊O���Ő錾�����ϐ����g���ꍇ�ɂ́A
	 * ���ׂẴN���C�A���g���܂����ŁA�l�����L�����̂ŁA���ӂ��K�v�ł��B
	 * A����̃p�\�R����B����̃p�\�R���ŁA���̃T�[�u���b�g���Ăяo�����Ƃ��ɁA
	 * ��l�Ƃ������ϐ����Q�Ƃ��邱�ƂɂȂ�܂��B</p>
	 * 
	 * ��j
	 * <ol>
	 * <li>A����̃A�N�Z�X1��� count = 1</li>
	 * <li>A����̃A�N�Z�X2��� count = 2</li>
	 * <li>B����̃A�N�Z�X1��� count = 3</li>
	 * <li>C����̃A�N�Z�X1��� count = 4</li>
	 * <li>A����̃A�N�Z�X3��� count = 5</li>
	 * </ol>
	 * 
	 * ���̂��߁A���\�b�h�̊O���Ő錾�����ϐ��Ɋe�N���C�A���g�̏��������ĉ�ʂɕ\������悤��
	 * �g����������ƁA�^�C�~���O�ɂ���ẮA�ϐ��ɒl���Z�b�g�����N���C�A���g�Ƃ́A
	 * �ʂ̃N���C�A���g�̉�ʂɁA�Z�b�g������񂪌����Ă��܂����Ƃ�����A
	 * �Z�L�����e�B��̖��ɂȂ邱�Ƃ�����܂��B</p>
	 * 
	 * ���\�b�h�̓����Ő錾�����ϐ��́A���̂悤�ȐS�z�͂���܂���B
	 * 
	 */
	int count = 0;

	/**
	 * �u���E�U��URL����͂���ƌĂяo����郁�\�b�h�ł��B
	 * 
	 * @param request ��ʂ���̗v�����e���܂ރI�u�W�F�N�g�ł��B
	 * @param response ��ʂւ̉������e���܂ރI�u�W�F�N�g�ł��B
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		GameSearchConditionBean conditionBean = new GameSearchConditionBean();
		request.setAttribute("conditionBean", conditionBean);

		// jsp�t�@�C���֏�����]�����܂��B
		// jsp�Ƃ́Ajava��html��g�ݗ��Ă�v���O�����̎d�g�݂ł��B
		// �T�[�o�[��ŁAjsp�̃v���O�����̋L�q���e�ɏ]���āAhtml��g�ݗ��ĂāA
		// �N���C�A���g�̃E�F�u�u���E�U�ɑ��M���܂��B
		getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/gemelist.jsp")
				.forward(request, response);
	}

	/**
	 * 
	 * ��ʂ���submit�{�^���������ƌĂяo����郁�\�b�h�ł��B
	 * 
	 * @param request ��ʂ���̗v�����e���܂ރI�u�W�F�N�g�ł��B
	 * @param response ��ʂւ̉������e���܂ރI�u�W�F�N�g�ł��B
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

		// ���������΍�̂��񑩂ł��B
		request.setCharacterEncoding("UTF-8");

		// ���������̃I�u�W�F�N�g�𐶐�����,��ʂ�����͂������������̏����Z�b�g���܂��B
		GameSearchConditionBean conditionBean = new GameSearchConditionBean();
		{
			String hardware = request.getParameter("hardware");
			String gameTitle = request.getParameter("gameTitle");
			if (hardware == null) {
				hardware = "";
			}
			if (gameTitle == null) {
				gameTitle = "";
			}

			conditionBean.setGameTitle(gameTitle);
			conditionBean.setHardware(hardware);
		}

		// ������������ʂɍČ������邽�߁A�N���C�A���g�ւ̉������e�Ɍ��������̃I�u�W�F�N�g���Z�b�g���܂��B
		// jsp�ŃN���C�A���g�̉�ʂ�g�ݗ��Ă�ۂɁArequest�I�u�W�F�N�g���猟�������̃I�u�W�F�N�g���Q�Ƃ��܂��B
		request.setAttribute("conditionBean", conditionBean);

		String error = "";

		// TODO ���C�ۑ� selectGameList�ŁA��ʂœ��͂������������ɉ������Q�[����Ԃ��������������Ă��������B
		{
			try {

				// �������s
				List<GameBean> gameList = selectGameList(conditionBean);

				// �������ʂ��Ajsp�ŎQ�Ƃł���悤��Request�ɃZ�b�g���܂��B
				request.setAttribute("gameList", gameList);

			} catch (ClassNotFoundException e) {
				error = "Oracle��JDBC�h���C�o��������܂���B" + e.getMessage();
				e.printStackTrace();
			} catch (SQLException e) {
				error = "SQL���Ԉ���Ă��邩�ADB�ɐڑ��ł��܂���B" + e.getMessage();
				e.printStackTrace();
			}

			// �N���C�A���g����v���񐔂���ʂɕ\�����܂��B
			count++;
			String message = count + "��ڂ̂���ɂ���";
			request.setAttribute("message", message);

			// �G���[���������ꍇ�A��L��catch�߂�error�ϐ��ɃG���[���b�Z�[�W���i�[���Ă��܂��B
			// �����JSP�ŎQ�Ƃł���悤��Request�ɃZ�b�g���Ă����܂��B
			request.setAttribute("error", error);

		}

		// gamelist.jsp��\�����܂��B
		getServletConfig().getServletContext().getRequestDispatcher("/WEB-INF/jsp/gemelist.jsp")
				.forward(request, response);

	}

	/**
	 * TODO ���C�ۑ� ���̃��\�b�h���������܂��B
	 * 
	 * �p�����[�^�Ƃ��ēn�������������ɍ��v����Q�[�����������āA���ʂ�List�ɓ���ĕԂ��܂��B
	 * 
	 * 
	 * @param conditionBean ��������
	 * @return 0���ȏ��Game��List�ɓ���ĕԂ��܂��B
	 * @throws ClassNotFoundException JDBC�h���C�o�N���X��������Ȃ��ꍇ��throw���܂��B
	 * @throws SQLException SQL���s�����ADB�ɐڑ��ł��Ȃ��ꍇ��throw���܂��B
	 * @throws IOException �v���p�e�B�t�@�C����ǂݍ��߂Ȃ��ꍇ��throw���܂��B 
	 * @throws FileNotFoundException �v���p�e�B�t�@�C�������݂��Ȃ��ꍇ��throw���܂��B 
	 */
	private List<GameBean> selectGameList(GameSearchConditionBean conditionBean) throws SQLException,
			ClassNotFoundException, FileNotFoundException, IOException {

		// ���ʂ̃Q�[�����i�[���邽�߂�List�̃C���X�^���X��V�����������܂��B
		// List�Ƃ́A�z������g���₷�������I�u�W�F�N�g�ł��B
		List<GameBean> gameList = new ArrayList<GameBean>();
		{
			// SQL��g�݂��Ă郁�\�b�h���Ăяo���܂��B
			String sql = getSqlOfSelectGameList(conditionBean);

			// DB�֐ڑ����郁�\�b�h���Ăяo���܂��B
			try (Connection connection = getConnection()) {

				// Statement��SQL�����s���邽�߂̃I�u�W�F�N�g�ł��B
				Statement statement = connection.createStatement();

				// SQL�̎��s���ʂ́AResultSet�ɓ����Ă��܂��B
				try (ResultSet result = statement.executeQuery(sql)) {

					// �����������R�[�h�̐������J��Ԃ��܂��B
					while (result.next()) {
						//TODO ���C�ۑ� �����ŁA�������ʂ�Java�̃I�u�W�F�N�g�ɕϊ����鏈�����L�q���܂��B
						GameBean gameBean = new GameBean();
						gameBean.setGameTitle(result.getString("DUMMY"));
						gameList.add(gameBean);
					}

				}
			}
		}

		// ��L�����őg�ݗ��Ă�List��Ԃ�l�Ƃ��Ė߂��܂��B
		return gameList;
	}

	/**
	 * properties�t�@�C���ɒ�`�����ڑ������ǂݍ��݂܂��B
	 * 
	 * @return
	 * @throws ClassNotFoundException JDBC�h���C�o�N���X��������Ȃ��ꍇ��throw���܂��B
	 * @throws SQLException SQL���s�����ADB�ɐڑ��ł��Ȃ��ꍇ��throw���܂��B
	 * @throws IOException �v���p�e�B�t�@�C����ǂݍ��߂Ȃ��ꍇ��throw���܂��B 
	 * @throws FileNotFoundException �v���p�e�B�t�@�C�������݂��Ȃ��ꍇ��throw���܂��B 
	 */
	private Connection getConnection() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {

		// properties�t�@�C���ǂݍ���
		DBConnectInfo info = new DBConnectInfo();

		Class.forName(info.getDriver());
		Connection connection = DriverManager.getConnection(info.getUrl(), info.getUser(), info.getPassword());

		return connection;
	}

	/**
	 * 
	 * ���������ɉ����ĕς��Q�[�������pSELECT��SQL�𕶎���ŕԂ��܂��B</p>
	 * 
	 * <����>
	 * �����ł͌��������𒼐ڕ�����ɑg�ݍ����SQL���쐬���܂��B</p>
	 * 
	 * �������������̂悤�ɁA��ʂ���̓��͓��œ��I�ɕς�镔���́A
	 * SQL�̕�����Ƃ͕ʂɂ��āA�ォ��ݒ�ł���悤�ɂ���̂�
	 * �Z�L�����e�B��p�t�H�[�}���X�ʂōs�V�̗ǂ��v���O�����Ƃ���Ă��܂��B
	 * (�o�C���h���J�j�Y���ƌ����܂��j
	 * ���̌��C�͂����܂ňӎ�����K�v�͂���܂���B</p>
	 * 
	 * ���̕��͂̈Ӗ����킩��Ȃ��Ă��A���̎��_�ł͋C�ɂ���K�v�͂���܂���B
	 * 
	 * @param conditionBean ��ʂœ��͂��������������i�[����Bean
	 * @return �����pSQL���i�[����������
	 */
	private String getSqlOfSelectGameList(GameSearchConditionBean conditionBean) {

		//TODO ���C�ۑ� ���������ɉ��������������邽�߂�SQL�������Ԃ��������L�q���Ă��������B

		StringBuilder sql = new StringBuilder();
		{
			sql.append("/*TODO ���C�ۑ� ����SQL��ҏW���āA�Q�[�����X�g���擾����SQL�ɕύX���Ă��������B*/" + "\n");
			sql.append("select	" + "\n");
			sql.append(" *		" + "\n");
			sql.append("from	" + "\n");
			sql.append("dual	" + "\n");
		}

		System.out.println(sql.toString());

		return sql.toString();

	}
}
