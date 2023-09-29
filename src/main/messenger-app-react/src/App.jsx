import './App.css'
import {useEffect, useState} from "react";
import {BrowserRouter, Link, Route, Routes, useNavigate, useParams} from "react-router-dom";


/**
 * @returns List of Clickable Users
 */
function FrontPage() {
    const [loading, setLoading] = useState(true);
    const [users, setUsers] = useState([]);

    useEffect(() => {
        (async () => {
            const res = await fetch("/api/user");
            setUsers(await res.json());
            setLoading(false);
        })();
    }, [])

    if (loading) {
        return <div>Loading...</div>
    }

    return (<div>
        <h1>Message App </h1>
        <ul>{users.map(value => <div>
            <h2 key={value.id}>
                <Link to={`/user/${value.id}`}>{value.name}</Link>
            </h2>
        </div>)}
        </ul>
    </div>);
}

/**
 * @returns Chat Rooms (Message Threads for A selected User)
 */
function ShowGroups() {
    const params = useParams();
    const userId = params.userId;
    const [group, setGroup] = useState([]);

    useEffect(() => {
        (async () => {
            const res = await fetch(`/api/usergroup/${userId}`);
            setGroup(await res.json());
        })();
    }, [])

    const names = group.map(value => value.userId.name);
    const ids = group.map(value => value.userId.id);
    const emails = group.map(value => value.userId.email);
    const phones = group.map(value => value.userId.phoneNumber);


    const userName = names[0];
    const idv = ids[0];
    const email = emails[0];
    const phone = phones[0];


    return (<div>
        <h1>Chat Rooms for {userName}</h1>
        <p>User Name: {userName} -- User Id: {idv} -- Email: {email} -- PhoneNr: {phone}</p>

        <ul>
            <h3><Link to={`/`}>Back to Homepage</Link></h3>
            <h3><Link to={`/user/${userId}/edit`}>Edit User Data</Link></h3>
            <h3><Link to={`/thread/new/${userId}`}>Create a new Message Thread</Link></h3>
            <h3><Link to={`/thread/new/${userId}/populate`}>Populate Thread</Link></h3>

            {group.map((value, index) => <h2 key={value.groupId.id}>
                {index + 1}. <Link to={`/thread/${value.groupId.id}/${userId}`}>{value.groupId.name}</Link>
            </h2>)}

        </ul>
    </div>)
}

/**
 *
 * @returns Messages in selected Group
 * @constructor
 */
function MessageThread() {
    const params = useParams();
    const groupId = params.groupId;
    const userId = params.userId;

    const [loading, setLoading] = useState(true);
    const [thread, setThread] = useState([]);
    const [message, setMessage] = useState("");
    const [title, setTitle] = useState("");
    const [users, setUsers] = useState([]);


    const border = {
        border: '1px solid rgb(204,204,0)',
    };

    useEffect(() => {
        (async () => {
            const res = await fetch(`/api/message/${groupId}`);
            setThread(await res.json());
            const resUsers = await fetch(`/api/usergroup/group/${groupId}`);
            setUsers(await resUsers.json());
            setLoading(false);
        })();
    }, [])

    const date = new Date();
    const usTime = date.toLocaleTimeString('en-US', {
        month: 'short', day: '2-digit', hour: '2-digit', minute: '2-digit',
    });

    const threadName = thread.map(value => value.groupId.name);
    const name = threadName[0];


    async function handleSubmit(e) {
        e.preventDefault();

        await fetch("/api/message", {
            method: "post", body: JSON.stringify({
                userId: {id: params.userId}, body: message, title: title, timeStamp: usTime, groupId: {id: groupId}
            }), headers: {
                "Content-Type": "application/json"
            }
        });

        // After posting, re-fetch messages
        const res = await fetch(`/api/message/${groupId}`);
        setThread(await res.json());
        setMessage("");
        setTitle("");
    }

    if (loading) {
        return (<div>
            <h1>Loading...</h1>
        </div>)
    }

    return (<div>
        <h1>{name}</h1>
        <p> People in this Chat :
            {users.map(value => <>{value.userId.name}, </>)}
        </p>

        <Link to={`/user/${userId}`}><h4>Back to Chat Rooms</h4></Link>


        <ul>{thread.map(value => <div key={value.id} style={border}>
            <div>Title: {value.title}</div>
            Sender --><u><strong>{value.userId.name}</strong></u>
            <div> Message --> {value.body}{}</div>
            <div> Time Stamp --> {value.timeStamp}{}</div>
        </div>)}
        </ul>
        <div>
            <form onSubmit={handleSubmit}>
                <label>
                    Title:
                    <input value={title} onChange={value => setTitle(value.target.value)}/>
                </label>
                <div><label>New Message: <textarea value={message}
                                                   onChange={value => setMessage(value.target.value)}/></label>
                </div>
                <button type={"submit"}>Send</button>
            </form>
        </div>
    </div>)
}


/**
 *
 * @returns Input Field for a new Message Thread. User is sent to their chatrooms after submitt button is clicked
 * @constructor
 */
function CreateNewThread() {
    const [groupName, setGroupName] = useState("");
    const params = useParams();
    const userId = params.userId;


    const navigate = useNavigate();


    function handleSubmit(e) {
        e.preventDefault();

        fetch("/api/group", {
            method: "post", body: JSON.stringify({name: groupName}), headers: {
                "Content-Type": "application/json"
            }
        });
        setGroupName("");
        navigate(`/user/${userId}`);
    }

    return (<div>
        <h1>Create New Thread</h1>
        <form onSubmit={handleSubmit}>
            <div><label>Group Name: <input value={groupName}
                                           onChange={value => setGroupName(value.target.value)}/></label>
            </div>
            <button type={"submit"}>Create Group</button>
        </form>
    </div>)
}


/**
 *
 * @returns Returns all Threads
 * @constructor
 */
function PopulateThread() {
    const [group, setGroup] = useState([]);
    const params = useParams();
    const userId = params.userId;

    useEffect(() => {
        (async () => {
            const groupRes = await fetch("/api/group");
            setGroup(await groupRes.json());
        })();
    }, [])


    return (<div>
        <h1>Select a Group To Populate</h1>
        <ul>
            <div>
                {group.map(value => <h2 key={value.id}>
                    <Link to={`/user/${userId}/${value.id}`}>{value.name}</Link>
                </h2>)}
            </div>
        </ul>
    </div>)
}

/**
 *
 * @returns Returns all users and input field to add user to selected group
 * @constructor
 */
function AddUsersToGroup() {
    const [users, setUsers] = useState([]);
    const [userIdFromPath, setUserIdFromPath] = useState("");
    const params = useParams();
    const userId = params.userId;
    const groupId = params.groupId;


    useEffect(() => {
        (async () => {
            const res = await fetch("/api/user");
            setUsers(await res.json());
        })();
    }, [])

    function handleSubmit(e) {
        e.preventDefault()
        fetch("/api/usergroup", {
            method: "post", body: JSON.stringify({userId: {id: userIdFromPath}, groupId: {id: groupId}}), headers: {
                "Content-Type": "application/json"
            }
        });
        setUserIdFromPath("");
    }

    return (<div>
        <h1>Which User do you want to Add to Group?</h1>
        <h4>Remember to add <u><strong>yourself</strong></u>, your Id is <u><strong>{userId}</strong></u></h4>
        <ul>{users.map(value => <div>
            <h3 key={value.id}>
                Name: {value.name} -- Id: {value.id}
            </h3>
        </div>)}
        </ul>
        <form onSubmit={handleSubmit}>
            <div><label>User Id: <input value={userIdFromPath}
                                        onChange={value => setUserIdFromPath(value.target.value)}/></label></div>
            <button type={"submit"}>Add User</button>
        </form>
        <Link to={"/"}>Home</Link>
    </div>);
}

/**
 *
 * @returns Input fields where a user can edit userInfo for selected user
 * @constructor
 */
function EditUser() {

    const [userData, setUserData] = useState([]);
    const [newName, setNewName] = useState("");
    const [newPhone, setNewPhone] = useState("");
    const [newEmail, setNewEmail] = useState("");


    const navigate = useNavigate();

    const params = useParams();
    const userId = params.userId;

    useEffect(() => {
        (async () => {
            const res = await fetch(`/api/user/${userId}`);
            setUserData(await res.json());
        })();
    }, [])


    function handleSubmit(e) {
        e.preventDefault()

        if (newName.length > 0) {
            name = newName
        }
        if (newEmail.length > 0) {
            email = newEmail
        }
        if (newPhone.length > 0) {
            phone = newPhone
        }

        fetch(`/api/user/${userId}`, {
            method: "post", body: JSON.stringify({id: userId, name: name, phoneNumber: phone, email: email}), headers: {
                "Content-Type": "application/json"
            }
        });
        navigate("/")
    }

    let name = userData.map(value => value.name).toString();
    let phone = userData.map(value => value.phoneNumber).toString();
    let email = userData.map(value => value.email).toString();


    return (<div>
        <h4>Edit User</h4>
        {userData.map(value => <h2>Name: {value.name} Email: {value.email} PhoneNr: {value.phoneNumber}</h2>)}
        <form onSubmit={handleSubmit}>
            <div>
                <div><label>Name: <input value={newName}
                                         onChange={value => setNewName(value.target.value)}/></label></div>
                <div><label>Email: <input value={newEmail}
                                          onChange={value => setNewEmail(value.target.value)}/></label></div>
                <div><label>Phone: <input value={newPhone}
                                          onChange={value => setNewPhone(value.target.value)}/></label></div>

            </div>
            <button type={"submit"}>Edit User</button>
        </form>
    </div>)
}

function Users() {
    return (<Routes>
        <Route path={"/:userId"} element={<ShowGroups/>}></Route>
        <Route path={"/:userId/:groupId"} element={<AddUsersToGroup/>}></Route>
        <Route path={":userId/edit"} element={<EditUser/>}></Route>


    </Routes>)
}


function App() {
    return (<BrowserRouter>
        <Routes>
            <Route path={"/"} element={<FrontPage/>}></Route>
            <Route path={"/user/*"} element={<Users/>}></Route>
            <Route path={"/thread/new/:userId"} element={<CreateNewThread/>}></Route>
            <Route path={"/thread/:groupId/:userId"} element={<MessageThread/>}></Route>
            <Route path={"/thread/new/:userId/populate"} element={<PopulateThread/>}></Route>
        </Routes>
    </BrowserRouter>)
}

export default App