<jsp:include page="header.jsp" />
<div class="edit-profile-container">
    <div class="section-title">
        <h1>EDIT PROFILE INFORMATION</h1>
    </div>

    <div class="update-profile-picture-container">
        <form id="upload-picture" method="post" action="profile_edit">
            <div class="upload-picture-container">
                <div class="square-image">
                    <img id="output" src="default.jpg">
                </div>
                <div class="uploader">
                    <label for="img_input">Update profile picture</label><br>
                    <div class="img-file-input">
                        <input type="file" class="file" name="img_input" id="img_input" />
                        <div class="dummy">
                            <input id="fake-input"/>
                            <div >
                                <p>Choose file</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="edit-name-phone-container">
                <ul>
                    <li>
                        <label for="full_name">Your Name</label>
                        <input type="text" name="full_name" value="${user.fullName}">
                    </li>
                    <li>
                        <label for="phone_number">Phone Number</label>
                        <input type="text" name="phone_number" value="${user.phoneNumber}">
                    </li>
                    <li>
                        <label for="is_driver">Status Driver</label>
                        <label class="driver-status-switch">
                            <input type="checkbox" name="is_driver" ${user.driver ? "checked" : ""}>
                            <span class="slider round"></span>
                        </label>
                    </li>
                </ul>
            </div>
            <div class="back-save-buttons">
                <div class="back-button">
                    <a href="profile">BACK</a>
                </div>
                <div class="save-button">
                    <input type="submit" value="SAVE" name="submit">
                </div>

            </div>
        </form>
    </div>
    <div class="location-container">

    </div>
</div>
<jsp:include page="footer.jsp" />