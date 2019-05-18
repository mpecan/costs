import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import {TableBody} from "@material-ui/core";
import React from "react";
import columns from '../columns';
import {withStyles} from "@material-ui/core/styles";

const styles = {
    container: {
        maxWidth: "90%",
        margin: "30px auto",
        overflow: "auto"
    }
};

function DataTable({classes, columnNames, data}) {
    return <Paper className={classes.container}>
        <Table>
            <TableHead>
                <TableRow key={"header"}>
                    {columnNames.map(name => <TableCell variant={"head"} key={name}>{columns[name]}</TableCell>)}
                </TableRow>
            </TableHead>
            <TableBody>
                {data.map(element =>
                    <TableRow key={element.id}>
                        {columnNames.map(name => <TableCell key={`${element.id}-${name}`}>{element[name]}</TableCell>)}
                    </TableRow>
                )}
            </TableBody>
        </Table>
    </Paper>;
}

export default withStyles(styles)(DataTable)