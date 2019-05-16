import React from 'react';
import './App.css';
import MainContainer from "./MainContainer";
import CssBaseline from "@material-ui/core/CssBaseline";
import {BreakpointProvider} from 'react-socks';

function App() {

    return (
        <div className="App">
            <CssBaseline/>
            <BreakpointProvider>
                <MainContainer/>
            </BreakpointProvider>
        </div>
    );
}

export default App;
