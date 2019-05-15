import React, {PureComponent} from "react";

import ExpandMoreIcon from "@material-ui/icons/ExpandMore"
import ExpansionPanel from "@material-ui/core/ExpansionPanel";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";
import Typography from "@material-ui/core/Typography";
import ExpansionPanelDetails from "@material-ui/core/ExpansionPanelDetails";
import {ValidatorForm, TextValidator} from 'react-material-ui-form-validator';
import {Button} from "@material-ui/core";
import * as _ from "lodash";
import withStyles from "@material-ui/core/es/styles/withStyles";
import Grid from "@material-ui/core/Grid";

const styles = {
    formElement: {
        margin: "10px"
    },
    form:{
        width: "100%"
    }
};


class Filters extends PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            minAverageCoveredCharges: "",
            maxAverageCoveredCharges: "",
            minAverageMedicarePayments: "",
            maxAverageMedicarePayments: "",
            minDischarges: "",
            maxDischarges: "",
            state: "",
        }
    }

    handleChange = name => event => {
        this.setState({[name]: event.target.value});

    };

    render() {
        const {classes} = this.props;
        return <ExpansionPanel>
            <ExpansionPanelSummary expandIcon={<ExpandMoreIcon/>}>
                <Typography>Filters</Typography>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails>

                <Grid container>
                    <ValidatorForm
                        className={classes.form}
                        ref="form"
                        onSubmit={() => this.props.changeFilters(Object.keys(this.state).filter((key) => this.state[key]).map((key) => `${_.snakeCase(key)}=${this.state[key]}`).join("&"))}
                        onError={errors => console.log(errors)}
                    >
                        <Grid container>
                            <Grid item xs={12} xl={3}>
                                <TextValidator
                                    className={classes.formElement}
                                    id="standard-name"
                                    label="Min Discharges"
                                    value={this.state.minDischarges}
                                    onChange={this.handleChange('minDischarges')}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    validators={['minNumber:0', `maxNumber:${this.state.maxDischarges || 930000}`, 'matchRegexp:^[0-9]+$']}
                                    errorMessages={['Cannot be less than 0', 'Cannot be more than Max Discharges', 'Must be an integer']}
                                    margin="normal"
                                />
                            </Grid>
                            <Grid item xs={12} xl={3}>
                                <TextValidator
                                    className={classes.formElement}
                                    id="standard-name"
                                    label="Max Discharges"
                                    value={this.state.maxDischarges}
                                    onChange={this.handleChange('maxDischarges')}
                                    validators={[`minNumber:${this.state.minDischarges || 0}`, 'matchRegexp:^[0-9]+$']}
                                    errorMessages={['Cannot be less than Min Discharges', 'Must be an integer']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                            </Grid>
                            <Grid item xs={12} xl={3}>
                                <TextValidator
                                    className={classes.formElement}
                                    id="standard-name"
                                    label="Min Average Covered Charges"
                                    value={this.state.minAverageCoveredCharges}
                                    onChange={this.handleChange('minAverageCoveredCharges')}
                                    validators={['minNumber:0', `maxNumber:${this.state.maxAverageCoveredCharges || 930000}`, 'matchRegexp:^[0-9]+([.,][0-9]+)?$']}
                                    errorMessages={['Cannot be less than 0', 'Cannot be more than Max Average Covered Charges', 'Must be a number']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                            </Grid>
                            <Grid item xs={12} xl={3}>
                                <TextValidator
                                    className={classes.formElement}
                                    id="standard-name"
                                    label="Max Average Covered Charges"
                                    value={this.state.maxAverageCoveredCharges}
                                    onChange={this.handleChange('maxAverageCoveredCharges')}
                                    validators={[`minNumber:${this.state.minAverageCoveredCharges || 0}`, 'matchRegexp:^[0-9]+([.,][0-9]+)?$']}
                                    errorMessages={['Cannot be less than Min Average Covered Charges', 'Must be a number ']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                            </Grid>
                            <Grid item xs={12} xl={3}>
                                <TextValidator
                                    className={classes.formElement}
                                    id="standard-name"
                                    label="Min Average Medicare Payments"
                                    value={this.state.minAverageMedicarePayments}
                                    onChange={this.handleChange('minAverageMedicarePayments')}
                                    validators={['minNumber:0', `maxNumber:${this.state.maxAverageMedicarePayments || 930000}`, 'matchRegexp:^[0-9]+([.,][0-9]+)?$']}
                                    errorMessages={['Cannot be less than 0', 'Cannot be more than Max Average Medicare Payments', 'Must be a number']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                            </Grid>
                            <Grid item xs={12} xl={3}>
                                <TextValidator
                                    className={classes.formElement}
                                    id="standard-name"
                                    label="Max Average Medicare Payments"
                                    value={this.state.maxAverageMedicarePayments}
                                    onChange={this.handleChange('maxAverageMedicarePayments')}
                                    validators={[`minNumber:${this.state.minAverageMedicarePayments || 0}`, 'matchRegexp:^[0-9]+([.,][0-9]+)?$']}
                                    errorMessages={['Cannot be less than Min Average Medicare Payments', 'Must be a number ']}
                                    InputLabelProps={{
                                        shrink: true,
                                    }}
                                    type="number"
                                    margin="normal"
                                />
                            </Grid>
                            <Grid item xs={12} xl={3}>
                                <TextValidator
                                    className={classes.formElement}
                                    id="standard-name"
                                    label="State"
                                    value={this.state.state}
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

export default withStyles(styles)(Filters);