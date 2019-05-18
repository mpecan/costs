import React, {PureComponent} from 'react';


import columns from './columns';
import CircularProgress from "@material-ui/core/CircularProgress";
import Filters from "./filter/Filters";
import AppBar from "@material-ui/core/AppBar";
import {withStyles} from "@material-ui/core/styles";
import TablePagination from "@material-ui/core/TablePagination";
import DataDisplay from "./dataDisplay/DataDisplay";
import {TableBody} from "@material-ui/core";
import TableRow from "@material-ui/core/TableRow";
import Table from "@material-ui/core/Table";
import ErrorDialog from "./ErrorDialog";

const styles = theme => ({
    root: {
        width: '100%',
        backgroundColor: theme.palette.background.paper,
    }
});


class MainContainer extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            page: 0,
            rowsPerPage: 20,
            isLoading: true,
            count: 0,
            data: [],
            filtersOpen: true,
            error: undefined,
            filters: ""
        };
        this.fetchData({});
    }

    fetchData({page = this.state.page, rowsPerPage = this.state.rowsPerPage, filters = this.state.filters}) {
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
        }).catch(error => {
            this.setState({...this.state, error: "Could not communicate to the server."});
        })
    }

    toggleFilters = (filtersOpen) => {
        this.setState({...this.state, filtersOpen});
    };

    changeFilters = (newFilters) => {
        this.setState({...this.state, filters: newFilters, filtersOpen: false});
        this.fetchData({filters: newFilters});
    };

    render() {
        const {data, isLoading, page, rowsPerPage, count, filtersOpen, error } = this.state;
        const columnNames = Object.keys(columns);
        return <div>
            <AppBar color="primary" position="static">
                <h1>Cost Explorer</h1>
                <Filters open={filtersOpen} toggle={this.toggleFilters}
                         changeFilters={this.changeFilters}/>
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
                            onChangePage={this.handleChangePage}
                            onChangeRowsPerPage={this.handleChangeRowsPerPage}
                        />
                    </TableRow>
                </TableBody>
            </Table>
            {error && <ErrorDialog content={error} close={() => this.setState({...this.state, error: null}, () => this.fetchData({})) } /> }
            {isLoading && <CircularProgress style={{marginLeft: '50%'}}/>}
            <DataDisplay data={data} columnNames={columnNames}/>
        </div>;
    }

    handleChangePage = (_, page) => {
        const {rowsPerPage} = this.state;
        this.fetchData({page, rowsPerPage})
    };

    handleChangeRowsPerPage = (event) => {
        const rowsPerPage = event.target.value;
        const {page} = this.state;
        this.fetchData({page, rowsPerPage});
    };
}


export default withStyles(styles)(MainContainer);