import React, { Component } from 'react';
import { ListGroup, ListGroupItem, Button, FormControl, Grid, FormGroup, ButtonToolbar, ToggleButtonGroup, ToggleButton, ControlLabel } from 'react-bootstrap';
import { getServers } from '../localStorageService';


class ServerSelect extends Component {
    state = {
        servers: getServers(),
        userInput: "",
        authType: 1
    }

    handleUserInput = proxy => {
        this.setState({
            ...this.state,
            userInput: proxy.target.value
        })
    }

    validateUserInput = () => {
        let connectionParts = this.state.userInput.split(":");
        if (connectionParts.length !== 2 || connectionParts[1] === "")
            return false

        let port = parseInt(connectionParts[1]);
        if (!port || port < 0 || port > 65535)
            return false;

        if (connectionParts[0] === 'localhost')
            return true;

        if (connectionParts[0].split(".").length !== 4)
            return false;

        return true;

    }
    handleAuthChange = (e) => {
        this.setState({
            ...this.state,
            authType: e
        })
    }
    render() {
        return (
            <Grid>
                <ListGroup>
                    {
                        this.state.servers.map(
                            (server, key) =>
                                <ListGroupItem key={`server${key}`}>
                                    {server.user}@{server.host}
                                </ListGroupItem>
                        )
                    }
                    <FormGroup>
                        <ControlLabel>IP and Port</ControlLabel>
                        <FormControl
                            type="text"
                            onChange={this.handleUserInput}
                            value={this.state.userInput}
                        />
                    </FormGroup>
                    <FormGroup>
                        <Button
                            disabled={!this.validateUserInput()}
                            className="full-width"
                            bsStyle="success">
                            +
                    </Button>
                    </FormGroup>
                    <FormGroup>
                        <ButtonToolbar>
                            <ToggleButtonGroup
                                type="radio"
                                name="options"
                                value={this.state.authType}
                                onChange={this.handleAuthChange}>
                                <ToggleButton value={1}>Register</ToggleButton>
                                <ToggleButton value={2}>Login</ToggleButton>
                            </ToggleButtonGroup>
                        </ButtonToolbar>
                    </FormGroup>
                    <FormGroup>
                        {
                            this.state.authType === 1 ?
                            <ControlLabel>Username</ControlLabel>
                            :
                            <ControlLabel>AccessKey</ControlLabel>
                        }
                        <FormControl
                            
                        />
                    </FormGroup>
                </ListGroup>
            </Grid>
        );
    }
}

export default ServerSelect;
