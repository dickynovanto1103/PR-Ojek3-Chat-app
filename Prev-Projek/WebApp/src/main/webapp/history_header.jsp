<jsp:include page="status.jsp" />
<h1>Transaction History</h1>
<nav class="navtab history-navtab">
    <a <%--class="<%= current_history_tab == "user" ? "selected" : "" %>"--%> href="history_user">My Previous Orders</a>
    <a <%--class="<%= current_history_tab == "driver" ? "selected" : "" %>"--%> href="history_driver">Driver History</a>
</nav>