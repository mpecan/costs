import Breakpoint from "react-socks";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import {List, ListItem, ListItemText, TableBody} from "@material-ui/core";
import columns from "./columns";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import React from "react";

export function DataDisplay({data, classes, columnNames}) {
    return <div>
        <Breakpoint medium down>
            {data.map(element => <Card key={element.id} className={classes.card}>
                <CardContent>
                    <List className={classes.itemContainer} key={`${element.id}-list`}>
                        {columnNames.map(name => <ListItem key={`${element.id}-${name}-item`}  className={classes.item}>
                            <ListItemText key={`${element.id}-${name}`} primary={columns[name]} secondary={element[name]}/>
                        </ListItem>)}
                    </List>
                </CardContent>
            </Card>)}
        </Breakpoint>
        <Breakpoint large up>
            <Paper className={classes.tableContainer}>
                <Table>
                    <TableHead>
                        <TableRow>
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
            </Paper>
        </Breakpoint>
    </div>;
}