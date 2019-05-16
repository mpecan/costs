import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import {List, ListItem, ListItemText} from "@material-ui/core";
import React from "react";
import {withStyles} from "@material-ui/core/styles";
import columns from '../columns';
import PropTypes from 'prop-types';

const styles = {
    card: {
        maxWidth: "90%",
        margin: "10px auto",

    },
    itemContainer: {
        padding: "0"
    },
    item: {
        padding: "5px"
    }
};


function DataCard({element, classes, columnNames}) {
    return <Card key={element.id} className={classes.card}>
        <CardContent>
            <List className={classes.itemContainer} key={`${element.id}-list`}>
                {columnNames.map(name => <ListItem key={`${element.id}-${name}-item`} className={classes.item}>
                    <ListItemText key={`${element.id}-${name}`} primary={columns[name]}
                                  secondary={element[name]}/>
                </ListItem>)}
            </List>
        </CardContent>
    </Card>;
}

DataCard.propTypes = {
    element: PropTypes.object.isRequired,
    columnNames: PropTypes.array.isRequired
};

export default withStyles(styles)(DataCard);