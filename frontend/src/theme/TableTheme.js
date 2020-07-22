import React from 'react';
import {createMuiTheme, MuiThemeProvider} from '@material-ui/core/styles';

function TableTheme({children}) {

	const getMuiTheme = () => createMuiTheme({
		overrides: {
			MUIDataTableBodyRow: {
				root: {
					lineHeight: "1",
					'&:nth-child(odd)': {
						backgroundColor: 'rgb(238, 238, 238)'
					}
				}
			}
		}
	});

	return (
		<MuiThemeProvider theme={getMuiTheme()}>
			{children}
		</MuiThemeProvider>
	);
}

export default TableTheme;
