import React from 'react';
import { Alert } from 'react-bootstrap';

const ErrorMessage = () =>
    <Alert bsStyle="danger">
        <strong>Error</strong>
        <br />
        Invalid Application State.
    </Alert>

export default ErrorMessage;