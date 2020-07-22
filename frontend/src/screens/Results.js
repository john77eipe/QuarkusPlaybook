import React from 'react';
import {Header} from "../components";
import MUIDataTable from "mui-datatables";
import TableTheme from "../theme/TableTheme";
import {Container, Grid} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';


const styles = (theme) => ({
	root: {
		display: 'flex',
		overflow: 'hidden',
	},
	container: {
		marginTop: theme.spacing(20),
		position: 'relative',
		display: 'flex',
	},
});

function Results(props) {

	const {classes} = props;

	const columns = [
		{
			name: "name",
			label: "Name",
		},
		{
			name: "status",
			label: "Status",
		},
		{
			name: "result",
			label: "Result",
		},
	];

	const options = {
		filterType: 'radio',
		// print: false,
		// download: false,
		viewColumns: false,
		// pagination: false,
		// selectableRows: false,
		delete: false,
		// search: false,
		// filter: false,
		disableToolbarSelect: true,
		setTableProps: () => {
			return {
				padding: "default",
				size: "small",
			};
		}
	};

	const tableData = [
		{
			"taskId": "sxtdrcfytvghbj1",
			"name": "Abcd1.java",
			"status": "Completed",
			"result": "Your input is processed successfully."
		},
		{
			"taskId": "sxtdrcfytvghbj2",
			"name": "Abcd2.java",
			"status": "Completed",
			"result": "Your input is processed successfully."
		},
		{
			"taskId": "sxtdrcfytvghbj3",
			"name": "Abcd3.java",
			"status": "Completed",
			"result": "Your input is processed successfully."
		},
		{
			"taskId": "sxtdrcfytvghbj4",
			"name": "Abcd4.java",
			"status": "Completed",
			"result": "Your input is processed successfully."
		},
		{
			"taskId": "sxtdrcfytvghbj5",
			"name": "Abcd5.java",
			"status": "Processing",
			"result": "Your input is processed successfully."
		}
	]

	return (
		<React.Fragment>
			<Header/>
			<section className={classes.root}>
				<Container className={classes.container}>
					<Grid container spacing={5}>
						<Grid item xs={12}>
							<TableTheme>
								<MUIDataTable
									data={tableData}
									columns={columns}
									options={options}
								/>
							</TableTheme>
						</Grid>
					</Grid>
				</Container>
			</section>

		</React.Fragment>
	);
}

export default withStyles(styles)(Results);