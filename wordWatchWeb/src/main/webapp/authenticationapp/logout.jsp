<html>
<body>
<%
           if(session!=null)
             {
                   session.invalidate();
%>
                <jsp:forward page="index.jsp" />
<%
              } else{
%>
            Logged Out Successfully....
<% }%>
</body>
</html>
