<%@page import="jp.co.tafs.kenshu.game.GameSearchConditionBean"%>
<%@page import="jp.co.tafs.kenshu.game.GameBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page language="java" import="jp.co.tafs.kenshu.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="javax.servlet.http.*" %>

<%/* 下の<jsp:useBean ...>の行は、
     GameSearchConditionBean conditionBean = request.getAttribute("conditionBean");
     ArrayList gameList = (ArrayList)request.getAttribute("gameList");
   と同じです。 */
   %>
<jsp:useBean id="conditionBean" scope="request" class="jp.co.tafs.kenshu.game.GameSearchConditionBean" />
<jsp:useBean id="gameList" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="kensuCount" scope="request" class="java.util.ArrayList" />
<jsp:useBean id="gameTitle" scope="request" class="java.util.ArrayList" />
<%-- <jsp:useBean id="deleteDate" scope="request" class="jp.co.tafs.kenshu.game.GameSearchConditionBean" />  --%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>ゲーム管理システム</title>
		<style type=text/css>
			#searchForm{
				background-color:lightblue;
			}
			tr th {
				background-color:gray;
				color:white;
				font-weight:bold;
			}
			tr td {
				background-color:white;
				color:green;
			}
			table,th,td {
				border:solid thin;
			}
			.float-left {
				float:left;
			}
			
		</style>
		<script> //検索を押した時の処理（課題１の３）
		 function kakunin(){
			
			 if(window.confirm("本当に実行していいんですか？後悔しないでくださいよ？")){
				 
				 return true;
			 }
			 else{
				 return false;
			 } 
				 
		 }
		function check(){
			if(document.searchForm.gameTitle.value==""){
				window.alert('ゲームタイトルを入力されてないよ');
				
			}
			else if(document.searchForm.hardware.value==""){
				window.alert('ハードウェアを入力されてないよ');
				
			}
			
	 }
		function deleteKakunin(){
			if(window.confirm("消すの？")){
			
				 return true;
			 }
			 else{
				 return false;
			 } 
		}
		
		
		
		</script>
	</head>
	<body>
	
		<h1>ゲーム管理システム</h1>
		
		<%/*
		 <form>の中に、<input>や<select>等、データを入力するためのコントロールを置きます。
		 formをsubmitすると、 これらのコントロールに入力した値が、サーバーにパラメータとして送信されます。
		 送信の方法(method)には、postとgetがあります。（他にもありますが、よく使うのはこの2種類）
		 postでformを送信すると、ServletのdoPost(request,response)が呼ばれ、getでformを送信すると、doGet(request,response)が呼ばれます。
		 postは、送信したパラメータの内容は見えません。getは、URLの後ろにパラメータが見える形でくっついて表示されます。
		 ブラウザのURL表示欄で、?があれば、その?以降がパラメータです。
		たとえば、
		<form method="get" action="mypage">
		<input type="text" name="hoge1" value="fuga1">
		<input type="text" name="hoge2" value="fuga2">
		<input type="hidden" name="hoge3" value="fuga3">
		</form>
		というフォームをsubmitすると、サブミット先は、
		 http://tafs.co.jp/app/mypage?hoge1=fuga1&hoge2=fuga2&hoge3=fuga3
		 となります。postの場合は?以降は見えません。見えてなくても、送信はされます。
		ブラウザのURL入力欄にURLを入力して、Enterキーを押した場合もgetでの呼び出しです。
		
		*/%>	
		<form id="searchForm" method="post" action="" onSubmit="return check()"name="searchForm">
			<h3>検索条件</h3>
			ゲームタイトル:<input type="text" name="gameTitle" value="<%=conditionBean.getGameTitle()  %>">
			ハードウェア:<input type="text" name="hardware" value="<%=conditionBean.getHardware()  %>">
			<p><input type="submit" value="検索" onClick="return kakunin();"></p> <!-- onClickによりクリックした後の処理を実現  -->
		</form>

		<p><%if(request.getAttribute("message")!=null) request.getAttribute("message");%></p>
		
		<p><%if(request.getAttribute("error")!=null) request.getAttribute("error") ;%></p>
		<hr>
		
		<%List<GameBean> dummyCount = new ArrayList<GameBean>();
			for (int i=0;i<kensuCount.size();i++){%>
			<%	dummyCount.add((GameBean)kensuCount.get(i));%>
			<p><%="検索件数："+ dummyCount.get(i).getKensu()+"件" %></p>
			<%}%> 
			
		<%System.out.println("aaa"+gameTitle.size()); %>	
		<% List<GameBean> dummy2 = new ArrayList<GameBean>();
		for (int i=0;i<gameTitle.size();i++){
		dummy2.add((GameBean)gameTitle.get(i));
		System.out.println(dummy2.get(i).getGameTitle());
		%>
		<p><%=dummy2.get(i).getGameTitle()+"を削除しました" %> </p>
		<%}%>
		<table class="float-left">
		
			<caption>ゲームマスタ一覧</caption>
			<tr>
				<th>No</th><th>ゲームタイトル</th><th>ハードウェア</th><th>感想</th><th>キャラクター数</th><th></th>
			</tr>
			<%List<GameBean> dummy = new ArrayList<GameBean>();
		
			for(int i = 0 ; i < gameList.size();i++){ %> 
			<%/*
			  * ここでgameListから、Servletで入れたGameBeanをどうやって値をとってくるかが、課題1の山場です。
			  * gameListの型は、Listです。
			  * http://docs.oracle.com/javase/jp/6/api/java/util/List.html
			  * http://docs.oracle.com/javase/jp/6/api/java/util/ArrayList.html
			  * この辺を見て、なんとかGameBeanを取り出してみてください。
			  *
			  */
			 	dummy.add((GameBean)gameList.get(i));
			
				
			%>
				<tr>
					<td><%=dummy.get(i).getGameId() %></td>
					<td><%=dummy.get(i).getGameTitle() %></td> 
					<td><%=dummy.get(i).getHardWare()%></td>
					<td><%=dummy.get(i).getImpression()%></td>
					<td><%=dummy.get(i).getCharaKensu()%></td>
					<td><form id="deleteColumn" method="post" action="del" name="deleteColumn"><input type="submit" value="削除" onClick="return deleteKakunin();"><input type="hidden" name="date" value=<%= dummy.get(i).getGameId() %>>
					</form></td>
				</tr>
			<%} %>
		</table>
	

		
		<div class="float-left" style="width:500px;margin-top:50px;margin-left:50px">
			課題１
			<ol>
			<li>htmlとcssを駆使して、画面デザインをかっこよくします。<br>
			かっこいいデザインが思いつかない人は、Webシステム仕様書を参考にしてください。</li>
			<li>始めの画面表示で、「null」という文字が表示されないようにします。</li>
			<li>検索を開始する前に、クライアント側のJavaScriptで処理を続行するかどうかの確認メッセージを表示します。
			<br>キャンセルした場合は検索実行を取りやめます。</li>
			<li>DBの研修課題で作ったゲームマスタの情報を画面下部の一覧に表示します。</li>
			</ol>

			課題２
			<ol>
			<li>検索条件に入力した内容で、SQLの検索条件を増やして、検索結果に反映させます。</li>
			<li>検索を開始する前にJavaScriptで入力をチェックします。<br>
				<ul>
					<li>ゲームタイトルに値が入力されていない場合にエラーメッセージ表示</li>
					<li>ハードウェアに値が入力されていない場合にエラーメッセージ表示</li>
				</ul>
			</li>
			</ol>

			課題３
			<ol>
			<li>ゲームマスタ一覧の検索結果件数を画面に表示します。<br>
			ただし、件数は、select count(*) from ...を使ってDBから取得してください。
			</li>
			<li>ゲームマスタ一覧に、キャラクタ数を表示する列をを追加してください。</li>
			</ol>

			課題４
			<ol>
			<li>各ゲームマスタ一覧の最終列に、削除ボタンを追加します。<br>
				削除ボタンを押すと、ゲームマスタの該当レコードを削除します。
				<ul>
					<li>htmlを編集し、削除ボタンを追加</li>
					<li>GameDeleteServletの追加<li>
					<li>web.xmlにservletを登録</li>
					<li>削除ボタンをクリックすると、GameDeleteServletにリクエストを送信する。</li>
					<li>GameDeleteServletでDBのレコード処理を実装</li>
					<li>一覧画面を再表示し、メッセージ「{ゲームタイトル}を削除しました。」を表示します。<br>
					（{ゲームタイトル}は削除したゲームタイトル名）
				</ul>
			</li>
			</ol>
			
			課題５
			<ol>
			<li>各自で自由にアレンジを加えてみましょう。</li>
			<li>Webシステム仕様書を参考に、新規登録・編集画面にも挑戦してみましょう。</li>
			</ol>
			
			
		</div>
	</body>
</html>