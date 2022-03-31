
//To get all users from the API
const getusers = () => {
    fetch('http://localhost:8080/UserManagementModule/api/viewusers')
            .then(res => res.json())
            .then(users => {
                CreateUserTable()
                for (const user of users) {
                    let userindex = users.indexOf(user) + 1
                    appendUsers(user, userindex)
                }
            })
}

//To accept each user and map to the component
const createUserTable = () => {
    while (userDiv.firstChild)
        userDiv.removeChild(userDiv.firstChild) // Remove all children from user div (if any)
    let userTable = document.createElement('table') // Create the table itself
    userTable.className = 'userTable'
    let userTableHead = document.createElement('thead') // Creates the table header group element
    userTableHead.className = 'userTableHead'
    let userTableHeaderRow = document.createElement('tr') // Creates the row that will contain the headers
    userTableHeaderRow.className = 'userTableHeaderRow'
// Will iterate over all the strings in the tableHeader array and will append the header cells to the table header row
    tableHeaders.forEach(header => {
        let userHeader = document.createElement('th') // Creates the current header cell during a specific iteration
        userHeader.innerText = header
        userTableHeaderRow.append(userHeader) // Appends the current header cell to the header row
    })
    userTableHead.append(userTableHeaderRow) // Appends the header row to the table header group element
    userTable.append(userTableHead)
    let userTableBody = document.createElement('tbody') // Creates the table body group element
    userTableBody.className = "userTable-Body"
    userTable.append(userTableBody) // Appends the table body group element to the table
    userDiv.append(userTable) // Appends the table to the user div
}