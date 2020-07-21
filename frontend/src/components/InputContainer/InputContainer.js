import React, {useState} from 'react';
import PropTypes from 'prop-types';
import {Button, Container, Grid, TextField, Typography} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';

const styles = (theme) => ({
	root: {
		display: 'flex',
		// backgroundColor: theme.palette.secondary.light,
		overflow: 'hidden',
	},
	container: {
		marginTop: theme.spacing(20),
		// marginBottom: theme.spacing(10),
		position: 'relative',
		display: 'flex',
		flexDirection: 'column',
		alignItems: 'center',
	},
	item: {
		display: 'flex',
		flexDirection: 'row',
		alignItems: 'center',
		padding: theme.spacing(0, 5),
	},
	button: {
		marginTop: theme.spacing(8),
	},
});


function InputContainer(props) {

	const {classes} = props;

	const [fileName, setFileName] = useState("");
	const [code, setCode] = useState("");
	const [sampleInput, setSampleInput] = useState("");
	const [loader, setLoader] = useState(true)

	if (loader) {
		return (
			<section className={classes.root}>
				<Container className={classes.container}>
					<div>Loading....</div>
				</Container>
			</section>)
	}

	return (
		<section className={classes.root}>
			<Container className={classes.container}>
				<div>
					<Grid container spacing={5}>
						<Grid item xs={12}>
							<div className={classes.item}>
								<TextField
									id="standard-full-width"
									label={<Typography variant="h5" align="center">
										Filename
									</Typography>}
									placeholder="Filename"
									fullWidth
									value={fileName}
									onChange={event => setFileName(event.target.value)}
									variant="outlined"
									margin="normal"
								/>
							</div>
						</Grid>
						<Grid item xs={12}>
							<div className={classes.item}>

								<TextField
									id="standard-full-width"
									label={<Typography variant="h5" align="center">
										Code
									</Typography>}
									multiline
									value={code}
									onChange={event => setCode(event.target.value)}
									rows={20}
									fullWidth
									variant="outlined"
								/>
							</div>
						</Grid>
						<Grid item xs={12}>
							<div className={classes.item}>
								<TextField
									id="standard-full-width"
									label={<Typography variant="h5" align="center">
										Sample Input
									</Typography>}
									placeholder="Sample Input"
									fullWidth
									value={sampleInput}
									onChange={event => setSampleInput(event.target.value)}
									variant="outlined"
									margin="normal"
								/>
							</div>
						</Grid>
						<Grid item xs={12}>
							<div className={classes.item}>
								<Typography variant="h5" align="center">
									Give a filename, write your code and click on the below "Show Magic" button.
								</Typography>
							</div>
						</Grid>
					</Grid>
				</div>
				<Button
					color="secondary"
					size="large"
					variant="contained"
					className={classes.button}
					component="a"
					onClick={event => {
						// 
						alert(fileName);
						alert(code);
						setLoader(true);

						// axios.
						// then {
						// setLoader(false);
						// }

						return false;
					}}
					href="/results"
				>
					Show Magic
				</Button>
			</Container>
		</section>
	);
}

InputContainer.propTypes = {
	classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(InputContainer);