function UserServiceClient(){
    this.createUser = createUser;
    this.findAllUsers = findAllUsers;
    this.findUserById = findUserById;
    this.deleteUser = deleteUser;
    this.updateProfile = updateProfile;
    this.updateUser = updateUser;
    this.register = register;
    this.login = login;
    this.logout = logout;
    this.getProfileUser = getProfileUser;
    this.sendEmail = sendEmail;
    this.resetPassword = resetPassword;
    this.selectUserByUserName = selectUserByUserName;
    this.url =
        '/api';
    var self = this; // self refers to this instance. It will be used later. this only refers to this object, not the whole class.

    function selectUserByUserName(username){
        return fetch(self.url + '/selectUserName/' + username)
            .then(function(response){
                return response.json();
            });
    }


    function resetPassword(user){
        return fetch(self.url + '/resetPassword', {
            method: 'put',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        });
    }


    function sendEmail(user){
        return fetch(self.url + '/forgotPassword', {
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        });

    }

    function login(username, password){
        return fetch(self.url + '/login', {
            method: 'post',
            body: JSON.stringify({username:username, password:password}),
            credentials: 'same-origin',
            headers: {
                'content-type': 'application/json'
            }
        });
    }

    function logout(){
        return fetch(self.url + '/logout', {
            method: 'post',
            credentials: 'same-origin',
            headers: {
                'content-type': 'application/json'
            }
        });
    }

    function updateUser(userId, user){
        return fetch(self.url + '/user/' + userId, {
            method: 'put',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        });
    }

    function updateProfile(user){
        return fetch(self.url + '/profile/updateProfile', {
            method: 'put',
            body: JSON.stringify(user),
            credentials: 'same-origin',
            headers: {
                'content-type': 'application/json'
            }
        });
    }

    function getProfileUser(){
        return fetch(self.url + '/profile',{
            method: 'get',
            credentials: 'same-origin',
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response){
                return response.json();
            });
    }


    function findUserById(userId){
        return fetch(self.url + '/user/' + userId)
            .then(function(response){
                return response.json();
            });
    }

    function deleteUser(userId){
        return fetch(self.url + '/user/' + userId, {
            method: 'delete'
        });
    }

    function findAllUsers(){
        // fetch admin from url. Get data from server rather than hard code.
        // default, fetch generates a get.
        // here we won't get a users, but a promise
        // register for the promise call back.
        return fetch(self.url + '/user/') // there is a return, note that two lines later there is another return
            .then(function(response){
                return response.json(); // We have to convert raw response to json for further use.
            });
    }


    function createUser(user){
        // send it over the server for storing
        // if fetch a post, we have to explicitly say.
        return fetch(self.url + '/user', {
            method: 'post',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }); // return a promise
    }


    function register(user){
        return fetch(self.url + '/register', {
            method: 'post',
            body: JSON.stringify(user),
            credentials: 'same-origin',
            headers: {
                'content-type': 'application/json'
            }
        });
    }

}