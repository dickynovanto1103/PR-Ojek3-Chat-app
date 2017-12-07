<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="header.jsp" />

<div class="profile-container">
  <div class="section-title">
    <h1>EDIT PREFERRED LOCATIONS</h1>
  </div>

  <form method="POST" action="profile_locations">
    <div class="edit-location-content-container">
      <table>
        <tr>
          <th id="no">No</th>
          <th id="location">Location</th>
          <th id="action">Actions</th>
        </tr>

        <% int i = 0; %>
        <c:forEach var="location" items="${locations}" >
        <tr>
          <td><%= ++i %></td>
          <td><input type="text" name="edit_name" value="${location.name}" disabled class="js-edit-field" data-id="<%= i %>"></td>
          <td>
            <input type="submit" value="&#10000;" formaction="profile_locations?action=update&id=${location.id}" class="js-edit-button" data-id="<%= i %>">
            <input type="submit" value="&#10060;" formaction="profile_locations?action=delete&id=${location.id}">
          </td>
        </tr>
        </c:forEach>
      </table>
    </div>

    <div class="add-new-location-container">
      <div class="section-title">
        <h2>ADD NEW LOCATION:</h2>
      </div>
      <div class="add-new-location">
        <div class="add-text-input">
          <input type="text" name="name">
        </div>

        <div class="add-button">
          <input type="submit" value="ADD" formaction="profile_locations">
        </div>
      </div>
    </div>
  </form>

  <div class="back-button">
    <a href="profile">BACK</a>
  </div>
</div>

<script>
let editButtonHandler = function (e) {
  let field = document.querySelector('.js-edit-field[data-id="' + e.target.dataset.id + '"]')

  if (field.disabled) {
    for (let dom of document.querySelectorAll('.js-edit-field')) {
      dom.disabled = true
    }
    field.disabled = false
    return false
  }

  return true
}

for (let dom of document.querySelectorAll('.js-edit-button')) {
  dom.onclick = editButtonHandler
}
</script>

<jsp:include page="footer.jsp" />
