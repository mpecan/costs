import React, {PureComponent} from "react";
import PropTypes from 'prop-types';
import ExpandMoreIcon from "@material-ui/icons/ExpandMore"
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import Typography from "@material-ui/core/Typography";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import {ValidatorForm, TextValidator} from 'react-material-ui-form-validator';
import {Button, Divider} from "@material-ui/core";
import * as _ from "lodash";
import {withStyles} from "@material-ui/core/styles";
import Breakpoint from "react-socks";
import Grid from "@material-ui/core/Grid";

const styles = {
    formElement: {
        margin: "10px"
    },
    form: {
        width: "100%"
    },
    divider: {
        margin: "10px"
    }
};

const filterNames = {
    minDischarges: "Min Discharges",
    maxDischarges: "Max Discharges",
    minAverageCoveredCharges: "Min Average Covered Charges",
    maxAverageCoveredCharges: "Max Average Covered Charges",
    minAverageMedicarePayments: "Min Average Medicare Payments",
    maxAverageMedicarePayments: "Max Average Medicare Payments",
    state: "State",
};


class Filters extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            filters: {
                minAverageCoveredCharges: "",
                maxAverageCoveredCharges: "",
                minAverageMedicarePayments: "",
                maxAverageMedicarePayments: "",
                minDischarges: "",
                maxDischarges: "",
                state: "",
            },
            filtersDisplay:"",
            panelExpanded: false
        }
    }

    handleChange = name => event => {
        this.setState({...this.state, filters: {...this.state.filters, [name]: event.target.value}});

    };

    onSubmit = () => {
        const definedFilters = Object.keys(this.state.filters).filter((key) => this.state.filters[key]);
        this.setState({...this.state, filtersDisplay: definedFilters.map((key) => `${filterNames[key]}=${this.state.filters[key]}`).join(", "), panelExpanded: false});
        this.props.changeFilters(definedFilters.map((key) => `${_.snakeCase(key)}=${this.state.filters[key]}`).join("&"));

    };

    render() {
        const {classes} = this.props;
        const {filtersDisplay, panelExpanded} = this.state;
        return <ExpansionPanel expanded={panelExpanded}  onChange={() => this.setState({...this.state, panelExpanded: !this.state.panelExpanded})}>
            <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>} >
                <Grid container><Grid item xs={12}><Typography variant={"title"}>Filters</Typography></Grid>{filtersDisplay && <Grid item xs={12}><Typography variant={"caption"}>{filtersDisplay}</Typography></Grid>}</Grid>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails >

                <Grid container>
                    <ValidatorForm
                        className={classes.form}
                        ref="form"
                        onSubmit={this.onSubmit}
                        onError={() => {
                        }}
                    >
                        <Grid container>
                            <Grid item xs={12} xl={6}>
                                <Typography>Discharges</Typography>
                                <TextValidator
                                    className={classes.formElement}
                                    id="minDischarges"
                                    label={"Min"}
                                    value={this.state.filters.minDischarges}
                                    onChange={this.handleChange('minDischarges')}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    validators={['minNumber:0', `maxNumber:${this.state.filters.maxDischarges || 930000}`, 'matchRegexp:^[0-9]+$']}
                                    errorMessages={['Cannot be less than 0', 'Cannot be more than Max Discharges', 'Must be an integer']}
                                    margin="normal"
                                />
                                <TextValidator
                                    className={classes.formElement}
                                    id="maxDischarges"
                                    label={"Max"}
                                    value={this.state.filters.maxDischarges}
                                    onChange={this.handleChange('maxDischarges')}
                                    validators={[`minNumber:${this.state.filters.minDischarges || 0}`, 'matchRegexp:^[0-9]+$']}
                                    errorMessages={['Cannot be less than Min Discharges', 'Must be an integer']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                                <Breakpoint medium down>
                                    <Divider className={classes.divider} />
                                </Breakpoint>
                            </Grid>
                            <Grid item xs={12} xl={6}>
                                <Typography>Average covered charges</Typography>
                                <TextValidator
                                    className={classes.formElement}
                                    id="minAverageCoveredCharges"
                                    label={"Min"}
                                    value={this.state.filters.minAverageCoveredCharges}
                                    onChange={this.handleChange('minAverageCoveredCharges')}
                                    validators={['minNumber:0', `maxNumber:${this.state.filters.maxAverageCoveredCharges || 930000}`, 'matchRegexp:^[0-9]+([.,][0-9]+)?$']}
                                    errorMessages={['Cannot be less than 0', 'Cannot be more than Max Average Covered Charges', 'Must be a number']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                                <TextValidator
                                    className={classes.formElement}
                                    id="maxAverageCoveredCharges"
                                    label={"Max"}
                                    value={this.state.filters.maxAverageCoveredCharges}
                                    onChange={this.handleChange('maxAverageCoveredCharges')}
                                    validators={[`minNumber:${this.state.filters.minAverageCoveredCharges || 0}`, 'matchRegexp:^[0-9]+([.,][0-9]+)?$']}
                                    errorMessages={['Cannot be less than Min Average Covered Charges', 'Must be a number ']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                                <Breakpoint medium down>
                                    <Divider className={classes.divider} />
                                </Breakpoint>
                            </Grid>
                            <Grid item xs={12} xl={6}>
                                <Typography>Average medicare payments</Typography>
                                <TextValidator
                                    className={classes.formElement}
                                    id="minAverageMedicarePayments"
                                    label={"Min"}
                                    value={this.state.filters.minAverageMedicarePayments}
                                    onChange={this.handleChange('minAverageMedicarePayments')}
                                    validators={['minNumber:0', `maxNumber:${this.state.filters.maxAverageMedicarePayments || 930000}`, 'matchRegexp:^[0-9]+([.,][0-9]+)?$']}
                                    errorMessages={['Cannot be less than 0', 'Cannot be more than Max Average Medicare Payments', 'Must be a number']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                                <TextValidator
                                    className={classes.formElement}
                                    id="maxAverageMedicarePayments"
                                    label={"max"}
                                    value={this.state.filters.maxAverageMedicarePayments}
                                    onChange={this.handleChange('maxAverageMedicarePayments')}
                                    validators={[`minNumber:${this.state.filters.minAverageMedicarePayments || 0}`, 'matchRegexp:^[0-9]+([.,][0-9]+)?$']}
                                    errorMessages={['Cannot be less than Min Average Medicare Payments', 'Must be a number ']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />

                                <Breakpoint medium down>
                                    <Divider className={classes.divider} />
                                </Breakpoint>
                            </Grid>
                            <Grid item xs={12} xl={6}>
                                <TextValidator
                                    className={classes.formElement}
                                    id="state"
                                    label={filterNames["state"]}
                                    value={this.state.filters.state}
                                    onChange={this.handleChange('state')}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    margin="normal"
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <Button type={"submit"}>Apply</Button>
                            </Grid>
                        </Grid>
                    </ValidatorForm>
                </Grid>
            </ExpansionPanelDetails>
        </ExpansionPanel>
    }
}

Filters.propTypes = {
    changeFilters: PropTypes.func.isRequired
};

export default withStyles(styles)(Filters);