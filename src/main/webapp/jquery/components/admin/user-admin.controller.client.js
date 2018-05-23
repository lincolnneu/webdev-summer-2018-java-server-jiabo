(function(){

    var $usernameFld, $passwordFld, $roleFld;
    var $removeBtn, $editBtn, $createBtn, $updateBtn, $searchBtn;
    var $firstNameFld, $lastNameFld;
    var $userRowTemplate, $tbody;
    var userService = new UserServiceClient();
    $(main);

    function main(){
        $tbody = $('tbody');
        $userRowTemplate = $('.wbdv-template');
        $createBtn = $('#createUser');
        $createBtn.click(createUser); // click event handler. When it is clicked, the callback will be called
        $searchBtn = $('#searchUser');
        $searchBtn.click(selectUser);

        findAllUsers();
    }

    function findAllUsers(){
        userService
            .findAllUsers()
            .then(renderUsers);
    }

    function createUser(){
        $usernameFld = $('#usernameFld').val(); // grab the value
        $passwordFld = $('#passwordFld').val();
        $firstNameFld = $('#firstNameFld').val();
        $lastNameFld = $('#lastNameFld').val();
        $roleFld = $('#roleFld').val();

        // an admin object that we can send over http request
        var user = new User($usernameFld,$passwordFld,null,$firstNameFld,$lastNameFld,null,$roleFld,null);

        userService
            .createUser(user)
            .then(function(response){
                if(response.status == 200){
                    findAllUsers();
                    alert("Congratulations! You just created a new user.");
                } else{
                    alert("The username is already taken. Please try other username");
                }
            });

    }

    function renderUsers(users){
        $tbody.empty(); // clear out the tbody.
        for(var i = 0; i < users.length; i++){ // generate contents from the array
            var user = users[i];
            var clone = $userRowTemplate.clone(); // in memory Dom element
            clone.attr('id', user.id); //Identifier. The generated html will be tagged with id.
            clone.find('.wbdv-username') // find the class in html.
                .html(user.username);
            clone.find('.wbdv-password')
                .html(user.password);
            clone.find('.wbdv-first-name')
                .html(user.firstName);
            clone.find('.wbdv-last-name')
                .html(user.lastName);
            clone.find('.wbdv-role')
                .html(user.role);
            clone.find('.wbdv-remove').click(deleteUser);
            clone.find('.wbdv-edit').click(editUser);

            $tbody.append(clone); //  append rows after the table.
        }
    }


    function renderUser(user) {
        $usernameFld = $("#usernameFld");
        $passwordFld = $("#passwordFld");
        $firstNameFld = $("#firstNameFld");
        $lastNameFld = $("#lastNameFld");
        $roleFld = $("#roleFld");
        $usernameFld.val(user.username);
        $passwordFld.val(user.password);
        $firstNameFld.val(user.firstName);
        $lastNameFld.val(user.lastName);
        $roleFld.val(user.role);
    }

    function deleteUser(event){
        $removeBtn = $(event.currentTarget); // raw DOM to jQuery Dom. Easier to manipulate
        var userId = $removeBtn
            .parent()
            .parent()
            .parent()
            .attr('id'); // admin id is grandgrandParentId

        userService
            .deleteUser(userId)
            .then(findAllUsers);
    }



    function updateUser(event) {
        $usernameFld = $('#usernameFld').val(); // grab the value
        $passwordFld = $('#passwordFld').val();
        $firstNameFld = $('#firstNameFld').val();
        $lastNameFld = $('#lastNameFld').val();
        $roleFld = $('#roleFld').val();
        // an admin object that we can send over http request
        var user = new User($usernameFld,$passwordFld,null,$firstNameFld,$lastNameFld,null,$roleFld,null);
        var userId = event.data;

        userService
            .updateUser(userId, user)
            .then(function(response){
                if(response.status == 200){
                    findAllUsers();
                    alert("Congratulations! User update success!");
                } else{
                    alert("Unable to update user.");
                }
            });

    }

    function selectUser() {
        $usernameFld = $('#usernameFld').val();
        userService
            .selectUserByUserName($usernameFld)
            .then(renderUser, function(){
                alert("This username does not match any record.");
            });


    }

    function findUserById(userId) {
        return userService
            .findUserById(userId)
    }

    function editUser(event){
        $editBtn = $(event.currentTarget);
        var userId = $editBtn
            .parent()
            .parent()
            .parent()
            .attr('id'); // admin id is grandgrandParentId

        findUserById(userId)
            .then(renderUser);

        $updateBtn = $('#updateUser');
        $updateBtn.click(userId,updateUser);

    }

})();