import React, {Component} from "react";
import Address from "./Address";
import Paper from "@material-ui/core/Paper";
import {connect} from "react-redux";
import {areTheSame, updateCustomerAddress, updateDeliveryAddress, isAddressValid} from "./redux";
import Switch from "@material-ui/core/Switch";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import {Grid} from "@material-ui/core";
import {Link as RouterLink} from "react-router-dom";
import Button from "@material-ui/core/Button";

class OrderDeliveryPage extends Component {

    constructor(props) {
        super(props);

        this.state = {
            customerAddressVisible: !areTheSame(props.deliveryAddress, props.customerAddress)
        }
        this.handleSwitch = this.handleSwitch.bind(this);
    }

    render() {
        return (
            <Paper style={{padding: "1em"}}>
                <Address label="Delivery address"
                         address={this.props.deliveryAddress}
                         onChange={this.props.updateDeliveryAddress}/>
                <FormControlLabel label="Customer address same as delivery address"
                                  control={
                                      <Switch
                                          checked={!this.state.customerAddressVisible}
                                          onChange={this.handleSwitch}
                                      />
                                  }/>
                {this.state.customerAddressVisible ?
                    <Address label="Customer address"
                             address={this.props.customerAddress}
                             onChange={this.props.updateCustomerAddress}/> : null}
                <Grid container direction="row"
                      spacing={2}
                      justify="space-evenly"
                      alignItems="center"
                      style={{marginTop: "1em"}}>
                    <Grid item>
                        <RouterLink to="/order/complete" style={{textDecoration: "name"}}>
                            <Button disabled={!isAddressValid(this.props.deliveryAddress) ||
                            (this.state.customerAddressVisible && !isAddressValid(this.props.customerAddress))}
                                    variant="contained"
                                    color="primary">
                                Continue
                            </Button>
                        </RouterLink>
                    </Grid>
                    <Grid item>
                        <RouterLink to="/basket" style={{textDecoration: "name"}}>
                            <Button variant="contained" color="secondary">Return
                            </Button>
                        </RouterLink>
                    </Grid>
                </Grid>
            </Paper>
        );
    }

    handleSwitch(event) {
        const customerAddressVisible = !event.target.checked;

        if (customerAddressVisible) {
            updateCustomerAddress()
        }
        this.setState({customerAddressVisible: !event.target.checked})
    }

    updateDeliveryAddress(address) {
        this.props.updateDeliveryAddress(address);

        if (!this.state.customerAddressVisible) {
            this.state.updateDeliveryAddress(address); //updateDeliveryAddress
        }
    }

    updateCustomerAddress(address) {
        this.props.updateCustomerAddress(address);
    }
}

const mapStateToProps = (state, ownProps) => {
    //TODO: when logged prefill with user address
    return {
        deliveryAddress: state.order.deliveryAddress,
        customerAddress: state.order.customerAddress
    }
}

const mapDispatchToProps = (dispatch, ownPops) => {
    return {
        updateDeliveryAddress: (address) => dispatch(updateDeliveryAddress(address)),
        updateCustomerAddress: (address) => dispatch(updateCustomerAddress(address))
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(OrderDeliveryPage)