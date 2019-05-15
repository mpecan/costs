import React from 'react';
import './App.css';
import DataDisplay from "./DataDisplayContainer";
import CssBaseline from "@material-ui/core/CssBaseline";
import {BreakpointProvider} from 'react-socks';

function App() {

    return (
        <div className="App">
            <BreakpointProvider>
                <CssBaseline>
                    <DataDisplay />
                </CssBaseline>
            </BreakpointProvider>
        </div>
    );
}

export default App;
