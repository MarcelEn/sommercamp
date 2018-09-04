const FIELD_NAME = "servers";

export const getServers = () => {
    if(localStorage.getItem(FIELD_NAME))
        return JSON.parse(localStorage.getItem(FIELD_NAME));
    return [];
}

export const addServer = server => {
    let servers = getServers();
    servers.push(server);
    localStorage.setItem(FIELD_NAME, JSON.stringify(servers));
}