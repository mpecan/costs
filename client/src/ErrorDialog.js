import React from 'react';
import PropTypes from 'prop-types';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import withMobileDialog from '@material-ui/core/withMobileDialog';

class ErrorDialog extends React.Component {
    render() {
        const { fullScreen, content, close } = this.props;

        return (
            <div>
                <Dialog
                    fullScreen={fullScreen}
                    open={true}
                    onClose={close}
                    aria-labelledby="responsive-dialog-title"
                >
                    <DialogTitle id="responsive-dialog-title">{"Error"}</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            {content}
                        </DialogContentText>
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={this.handleClose} color="primary" autoFocus>
                            Try again?
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        );
    }
}

ErrorDialog.propTypes = {
    fullScreen: PropTypes.bool.isRequired,
    content: PropTypes.string.isRequired,
    close: PropTypes.func.isRequired
};

export default withMobileDialog()(ErrorDialog);