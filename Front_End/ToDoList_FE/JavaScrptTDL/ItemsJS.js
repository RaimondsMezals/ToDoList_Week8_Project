'use strict';

const addListBtn = document.querySelector(".addButton");
const updateListBtn = document.querySelector("#updateListBtn");
const deleteListBtn = document.querySelector("#deleteListBtn");
const readIdBtn = document.querySelector("#readListIdBtn");

const itemName = document.querySelector("#itemName");
const itemPrice = document.querySelector("#itemPrice");
const bagId = document.querySelector("#bagId");
const listIdToDelete = document.querySelector("#listIdToDelete");
const listId = document.querySelector("#listId");
const updateListName = document.querySelector("#updateListName");
const updateItemPrice = document.querySelector("#updateItemPrice");
const listIdToRead = document.querySelector("#listIdToRead");

let successAddL = document.querySelector("#addListPara");
let failureAddL = document.querySelector("#addListPara");

let successUpdateL = document.querySelector("#updateListPara");
let failureUpdateL = document.querySelector("#updateListPara");

let successDeleteL = document.querySelector("#deleteListPara");
let failureDeleteL = document.querySelector("#deleteListPara");

let readIdL = document.querySelector("#thisList");

const getItem = () => {
    const data = {
        "itemName": itemName.value,
        "price": itemPrice.value,
        "bag": {
            "id": bagId.value
        }
    };
    return data;
}

const getlistId = () => {
    const data = listId.value;
    return data;
}
const getUpdateItem = () => {
    const data = {
        "itemName": updateListName.value,
        "price": updateItemPrice.value,
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

const createItem = () => {
    fetch("http://localhost:9090/item/create", {
        method: `POST`,
        body: JSON.stringify(getItem()),
        headers: {
            "Content-type": "application/json"
        }
    })
        .then((res) => {
            if (res.status == 201) {
                successAddL.innerHTML = "Successfully Created a (" + itemName.value + ") Item";
            } else {
                failureAddL.innerHTML = "Failed to create a (" + itemName.value + ") Item";
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

const updateItem = () => {
    fetch("http://localhost:9090/item/update/" + getlistId(), {
        method: `PUT`,
        body: JSON.stringify(getUpdateItem()),
        headers: {
            "Content-type": "application/json"
        },
    })
        .then((res) => {
            if (res.status == 202) {
                successUpdateL.innerHTML = "Successfully Updated (" + updateListName.value + ") Item";
            } else {
                failureUpdateL.innerHTML = "Failed to update (" + updateListName.value + ") Item";
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

const deleteItem = () => {
    fetch("http://localhost:9090/item/delete/" + getListIdToDelete(), {
        method: "DELETE"
    })
        .then((data) => {
            if (data.status == 204) {
                console.info(`Request was all good with json response ${data}`)
                successDeleteL.innerHTML = "Deleted an Item with id (" + listIdToDelete.value + ")";
            } else {
                failureDeleteL.innerHTML = "Item with id (" + listIdToDelete.value + ") does not exist";
                console.error(data.status);
            }
        })
        .catch((err) => {
            console.error(err)
        });
}

const readItemById = () => {
    fetch("http://localhost:9090/item/read/" + getReadListId())
        .then((response) => {
            if (response.status !== 200) {
                readIdL.innerHTML = "Item with id (" + getReadListId() + ") does not exist";
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




addListBtn.addEventListener("click", createItem);
updateListBtn.addEventListener("click", updateItem);
deleteListBtn.addEventListener("click", deleteItem);
readIdBtn.addEventListener("click", readItemById);