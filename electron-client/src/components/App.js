import React, { Component } from 'react';
import ErrorMessage from './ErrorMessage';
import ServerSelect from './ServerSelect';


const applicationState = {
  SEVER_SELECT: 'SEVER_SELECT'
}


class App extends Component {
  state = {
    applicationState: applicationState.SEVER_SELECT
  }


  render() {

    switch (this.state.applicationState) {
      case applicationState.SEVER_SELECT:
        return <ServerSelect />
    }
    return <ErrorMessage />;
  }
}

export default App;
