'use strict';

const addListBtn = document.querySelector(".addButton");
const updateListBtn = document.querySelector("#updateListBtn");
const deleteListBtn = document.querySelector("#deleteListBtn");
const readIdBtn = document.querySelector("#readListIdBtn");

const listName = document.querySelector("#listName");
const listIdToDelete = document.querySelector("#listIdToDelete");
const listId = document.querySelector("#listId");
const updateListName = document.querySelector("#updateListName");
const listIdToRead = document.querySelector("#listIdToRead");

let successAddL = document.querySelector("#addListPara");
let failureAddL = document.querySelector("#addListPara");

let successUpdateL = document.querySelector("#updateListPara");
let failureUpdateL = document.querySelector("#updateListPara");

let successDeleteL = document.querySelector("#deleteListPara");
let failureDeleteL = document.querySelector("#deleteListPara");

let readIdL = document.querySelector("#thisList");

const getListName = () => {
    const data = {
        "listName": listName.value
    };
    return data;
}

const getlistId = () => {
    const data = listId.value;
    return data;
}
const getUpdateListName = () => {
    const data = {
        "listName": updateListName.value
    };
    return data;
}

const getListIdToDelete = () => {
    const data = listIdToDelete.value;
    return data;
}

const getReadListId = () => {
    const data = listIdToRead.value;
    return data;
}

const createBag = () => {
    fetch("http://localhost:9090/bag/create", {
        method: `POST`,
        body: JSON.stringify(getListName()),
        headers: {
            "Content-type": "application/json"
        },
    })
        .then((res) => {
            if (res.status == 201) {
                successAddL.innerHTML = "Successfully Created a (" + listName.value + ") List";
            } else {
                failureAddL.innerHTML = "Failed to create a (" + listName.value + ") List";
                console.error(res.status);
            }
            res.json()
        })
        .then((data) => {
            console.info(`Request was all good with json response ${data}`)
        })
        .catch((err) => {
            console.error(err)
        })
}

const updateBag = () => {
    fetch("http://localhost:9090/bag/update/" + getlistId(), {
        method: `PUT`,
        body: JSON.stringify(getUpdateListName()),
        headers: {
            "Content-type": "application/json"
        },
    })
        .then((res) => {
            if (res.status == 202) {
                successUpdateL.innerHTML = "Successfully Updated (" + updateListName.value + ") List";
            } else {
                failureUpdateL.innerHTML = "Failed to update (" + updateListName.value + ") List";
                console.error(res.status);
            }
            res.json()
        })
        .then((data) => {
            console.info(`Request was all good with json response ${data}`)
        })
        .catch((err) => {
            console.error(err)
        });
}

const deleteBag = () => {
    fetch("http://localhost:9090/bag/delete/" + getListIdToDelete(), {
        method: "DELETE"
    })
        .then((data) => {
            if (data.status == 204) {
                console.info(`Request was all good with json response ${data}`)
                successDeleteL.innerHTML = "Deleted a List with id (" + listIdToDelete.value + ")";
            } else {
                failureDeleteL.innerHTML = "List with id (" + listIdToDelete.value + ") does not exist";
                console.error(data.status);
            }
        })
        .catch((err) => {
            console.error(err)
        });
}

const readBagById = () => {
    fetch("http://localhost:9090/bag/read/" + getReadListId())
        .then((response) => {
            if (response.status !== 200) {
                readIdL.innerHTML = "List with id (" + getReadListId() + ") does not exist";
                console.error(`status ${response.status}`);
                return;
            }
            response.json().then((data) => {
                let item = JSON.stringify(data)
                readIdL.innerHTML = item;
                console.info(data);
            });
        })
        .catch((err) => {
            console.error(err)
        });
}




addListBtn.addEventListener("click", createBag);
updateListBtn.addEventListener("click", updateBag);
deleteListBtn.addEventListener("click", deleteBag);
readIdBtn.addEventListener("click", readBagById);