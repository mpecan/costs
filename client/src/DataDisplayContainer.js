import React, {PureComponent} from 'react';


import columns from './columns';
import CircularProgress from "@material-ui/core/CircularProgress";
import Filters from "./filter/Filters";
import AppBar from "@material-ui/core/AppBar";
import {withStyles} from "@material-ui/core/styles";
import TablePagination from "@material-ui/core/TablePagination";
import {DataDisplay} from "./DataDisplay";
import {TableBody} from "@material-ui/core";
import TableRow from "@material-ui/core/TableRow";
import Table from "@material-ui/core/Table";

const styles = theme => ({
    root: {
        width: '100%',
        maxWidth: 360,
        backgroundColor: theme.palette.background.paper,
    },
    card: {
        maxWidth: "90%",
        margin: "10px auto",

    },
    tableContainer: {
        maxWidth: "90%",
        margin: "30px auto",

    },
    itemContainer: {
        padding: "0"
    },
    item: {
        padding: "5px"
    }

});


class DataDisplayContainer extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            page: 0,
            rowsPerPage: 20,
            isLoading: true,
            count: 0,
            data: [],
            filtersOpen: true
        };
        this.fetchData({});
    }

    fetchData({page = this.state.page, rowsPerPage = this.state.rowsPerPage, filters = ""}) {
        fetch(`/api/providers?page=${page}&size=${rowsPerPage}${filters ? "&" + filters : ""}`).then(response => {
            if (response.status === 200) {
                return response.json();
            }
        }).then(json => {
            this.setState({
                ...this.state,
                data: json.content,
                isLoading: false,
                count: json.totalPages,
                page: json.number,
                rowsPerPage: json.numberOfElements
            });
        })
    }

    toggleFilters = (filtersOpen) => {
        this.setState({...this.state, filtersOpen})
    };


    render() {
        const {classes} = this.props;
        const {data, isLoading, page, rowsPerPage, count, filtersOpen} = this.state;
        const columnNames = Object.keys(columns);
        return <div>
            <AppBar color="primary" position="static">
                <h1>Providers</h1>

                <Filters open={filtersOpen} toggle={this.toggleFilters}
                         changeFilters={(newFilters) => this.fetchData({filters: newFilters})}/>
            </AppBar>
            <Table>
                <TableBody>
                    <TableRow>
                        <TablePagination
                            rowsPerPageOptions={[20, 50, 100]}
                            colSpan={3}
                            count={count}
                            rowsPerPage={rowsPerPage}
                            page={page}
                            SelectProps={{
                                native: true,
                            }}
                            onChangePage={(_, number) => this.handleChangePage(number)}
                            onChangeRowsPerPage={(event) => this.handleChangeRowsPerPage(event.target.value)}
                        />
                    </TableRow>
                </TableBody>
            </Table>
            {isLoading && <CircularProgress style={{marginLeft: '50%'}}/>}
            <DataDisplay data={data} classes={classes} columnNames={columnNames}/>
        </div>;
    }

    handleChangePage(page) {
        const {rowsPerPage} = this.state;
        this.fetchData({page, rowsPerPage})
    }

    handleChangeRowsPerPage(rowsPerPage) {
        const {page} = this.state;
        this.fetchData({page, rowsPerPage});
    }
}


export default withStyles(styles)(DataDisplayContainer);