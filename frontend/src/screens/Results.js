import React, {useEffect, useState} from 'react';
import {Header} from "../components";
import MUIDataTable from "mui-datatables";
import TableTheme from "../theme/TableTheme";
import {Container, Grid} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import axios from 'axios';

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
	const [tableData, setTableData] = useState([]);

	useEffect(() => {
		axios({
			method: 'GET',
			url: 'http://localhost:8080/code/results',
		})
			.then((response) => {
				console.log(response.data);
				setTableData(response.data);
			}, (error) => {
				console.log(error);
			});
	}, [])

	const columns = [
		{
			name: "name",
			label: "Name",
		},
		{
			name: "result",
			label: "Result",
		},
	];

	const options = {
		filterType: 'radio',
		viewColumns: false,
		delete: false,
		disableToolbarSelect: true,
	};

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