import Breakpoint from "react-socks";
import React from "react";
import DataCard from "./DataCard";
import DataTable from "./DataTable";

export default function DataDisplay({data, columnNames}) {
    return <div>
        <Breakpoint key={"small"} medium down>
            {data.map(element => <DataCard key={element.id} element={element} columnNames={columnNames}/>)}
        </Breakpoint>
        <Breakpoint key={"large"} large up>
            <DataTable columnNames={columnNames} data={data}/>
        </Breakpoint>
    </div>;
}