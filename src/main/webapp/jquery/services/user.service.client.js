function UserServiceClient(){
    this.createUser = createUser; // if the following is not defined, please comment them
    // out, or the browser will not run this js properly.
    this.findAllUsers = findAllUsers;
    this.findUserById = findUserById;
    this.deleteUser = deleteUser;
    this.updateUser = updateUser;
    this.register = register;
    this.url =
        'http://localhost:8080/api';
    var self = this; // self refers to this instance. It will be used later. this only refers to this object, not the whole class.

    function updateUser(userId, user){
        return fetch(self.url + '/user/' + userId, {
            method: 'put',
            body: JSON.stringify(user),
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response){
            if(response.ok){ // here should use ok rather than bodyused.
                return response.json();
            }else{
                return null;
            }

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
            headers: {
                'content-type': 'application/json'
            }
        }).then(function(response){
            return response.json(); // We have to convert raw response to json for further use.
        });
    }

}